package cn.bestsort.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import cn.bestsort.constant.ExceptionConstant;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bestsort.model.dto.PermissionDTO;
import cn.bestsort.model.entity.Permission;
import cn.bestsort.model.entity.RolePermission;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.service.PermissionService;
import cn.bestsort.service.RolePermissionService;
import cn.bestsort.util.UserUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 权限相关接口
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService iRolePermission;

    @ApiOperation(value = "当前登录用户拥有的权限")
    @GetMapping("/current")
    public List<PermissionDTO> permissionsCurrent() {
        LoginUserVO loginUserVO = UserUtil.getLoginUser();
        List<PermissionDTO> list = loginUserVO.getPermissions();
        final List<PermissionDTO> permissionDTOS = list.stream().filter(l -> l.getType().equals(1))
            .collect(Collectors.toList());
        List<PermissionDTO> firstLevel = permissionDTOS
            .stream().filter(p -> p.getParentId().equals(0L)).collect(Collectors.toList());
        firstLevel.parallelStream().forEach(p -> {
            setChild(p, permissionDTOS);
        });
        return firstLevel;
    }

    /**
     * 设置子元素
     * 2018.06.09
     *
     * @param p
     * @param permissionDTOS
     */
    private void setChild(PermissionDTO p, List<PermissionDTO> permissionDTOS) {
        List<PermissionDTO> child = permissionDTOS
            .parallelStream().filter(a -> a.getParentId().equals(p.getId())).collect(Collectors.toList());
        p.setChild(child);
        if (!CollectionUtils.isEmpty(child)) {
            child.parallelStream().forEach(c -> {
                //递归设置子元素，多级菜单支持
                setChild(c, permissionDTOS);
            });
        }
    }

    /**
     * 菜单列表
     *
     * @param pId
     * @param permissionsAll
     * @param list
     */
    private void setPermissionsList(Long pId, List<PermissionDTO> permissionsAll, List<PermissionDTO> list) {
        for (PermissionDTO per : permissionsAll) {
            if (per.getParentId().equals(pId)) {
                list.add(per);
                if (permissionsAll.stream()
                    .anyMatch(p -> p.getParentId().equals(per.getId()))) {
                    setPermissionsList(per.getId(), permissionsAll, list);
                }
            }
        }
    }

    @GetMapping
    @ApiOperation(value = "菜单列表")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public List<PermissionDTO> permissionsList() {
        List<Permission> permissionsAll = permissionService.listAll();

        List<PermissionDTO> list = Lists.newArrayList();
        setPermissionsList(0L, permissionsAll.stream()
                .map(PermissionDTO::transForm)
                .collect(Collectors.toList()),
            list);

        return list;
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有菜单")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public JSONArray permissionsAll() {
        List<Permission> permissionsAll = permissionService.listAll();
        JSONArray array = new JSONArray();
        setPermissionsTree(0L, permissionsAll.stream()
            .map(PermissionDTO::transForm)
            .collect(Collectors.toList()), array);

        return array;
    }

    @GetMapping("/parents")
    @ApiOperation(value = "一级菜单")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public List<PermissionDTO> parentMenu() {
        //TODO change
        List<Permission> parents = permissionService.listByType(1);

        return parents.stream()
            .map(PermissionDTO::transForm)
            .collect(Collectors.toList());
    }

    /**
     * 菜单树
     *
     * @param pId
     * @param permissionsAll
     * @param array
     */
    private void setPermissionsTree(Long pId, List<PermissionDTO> permissionsAll, JSONArray array) {
        for (PermissionDTO per : permissionsAll) {
            if (per.getParentId().equals(pId)) {
                String string = JSONObject.toJSONString(per);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);

                if (permissionsAll.stream().anyMatch(p -> p.getParentId().equals(per.getId()))) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(per.getId(), permissionsAll, child);
                }
            }
        }
    }

    @GetMapping(params = "roleId")
    @ApiOperation(value = "根据角色id获取权限")
    @PreAuthorize("hasAnyAuthority('sys:menu:query','sys:role:query')")
    public List<PermissionDTO> listByRoleId(Long roleId) {
        // 1. 根据roleId在role-permission关系表中找到去重后的permissionId集合
        // 2. 根据Id执行in语句并转换成PermissionDTO
        return permissionService.listAllByIds(
            iRolePermission.listByRoleId(roleId)
                .stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet()))
            .stream()
            .map(PermissionDTO::transForm)
            .collect(Collectors.toList());
    }


    @PostMapping
    @ApiOperation(value = "保存菜单")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public void save(@RequestBody @Valid Permission permission) {
        if (permissionService.findByName(permission.getName()) != null) {
            throw new IllegalArgumentException(permission.getName() + "已存在");
        } else {
            permissionService.save(permission);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据菜单id获取菜单")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public PermissionDTO get(@PathVariable Long id) {
        return PermissionDTO.transForm(permissionService.getById(id));
    }


    @PutMapping
    @ApiOperation(value = "修改菜单")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public void update(@RequestBody @Valid Permission permission) {
        permissionService.update(permission, permission.getId());
    }

    /**
     * 校验权限
     *
     * @return
     */
    @GetMapping("/owns")
    @ApiOperation(value = "校验当前用户的权限")
    public Set<String> ownsPermission() {
        List<PermissionDTO> permissions = Objects.requireNonNull(UserUtil.getLoginUser()).getPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptySet();
        }

        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
            .map(PermissionDTO::getPermission).collect(Collectors.toSet());
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除菜单")
    @PreAuthorize("hasAuthority('sys:menu:del')")
    public void delete(@PathVariable Long id) {
        if (iRolePermission.listByPermissionId(id) != null) {
            throw ExceptionConstant.DICTIONARY_IN_USE;
        }
        permissionService.delete(id);
    }
}
