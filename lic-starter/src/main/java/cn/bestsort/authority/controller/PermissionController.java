package cn.bestsort.authority.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bestsort.authority.dao.PermissionDao;
import cn.bestsort.authority.dto.LoginUser;
import cn.bestsort.authority.model.Permission;
import cn.bestsort.authority.model.PermissionDTO;
import cn.bestsort.authority.service.PermissionService;
import cn.bestsort.authority.utils.UserUtil;
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
    private PermissionDao permissionDao;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "当前登录用户拥有的权限")
    @GetMapping("/current")
    public List<PermissionDTO> permissionsCurrent() {
        LoginUser loginUser = UserUtil.getLoginUser();
        List<PermissionDTO> list = loginUser.getPermissions();
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
                    .filter(p -> p.getParentId().equals(per.getId()))
                    .findAny() != null) {
                    setPermissionsList(per.getId(), permissionsAll, list);
                }
            }
        }
    }

    @GetMapping
    @ApiOperation(value = "菜单列表")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public List<PermissionDTO> permissionsList() {
        List<Permission> permissionsAll = permissionDao.listAll();

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
        List<Permission> permissionsAll = permissionDao.listAll();
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
        List<Permission> parents = permissionDao.listParents();

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
        return permissionDao
            .listByRoleId(roleId)
            .stream()
            .map(PermissionDTO::transForm)
            .collect(Collectors.toList());
    }


    @PostMapping
    @ApiOperation(value = "保存菜单")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public void save(@RequestBody PermissionDTO permissionDTO) {
        permissionDao.save(permissionDTO);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据菜单id获取菜单")
    @PreAuthorize("hasAuthority('sys:menu:query')")
    public PermissionDTO get(@PathVariable Long id) {
        return PermissionDTO.transForm(permissionDao.getById(id));
    }


    @PutMapping
    @ApiOperation(value = "修改菜单")
    @PreAuthorize("hasAuthority('sys:menu:add')")
    public void update(@RequestBody PermissionDTO permissionDTO) {
        permissionService.update(permissionDTO);
    }

    /**
     * 校验权限
     *
     * @return
     */
    @GetMapping("/owns")
    @ApiOperation(value = "校验当前用户的权限")
    public Set<String> ownsPermission() {
        List<PermissionDTO> permissionDTOS = Objects.requireNonNull(UserUtil.getLoginUser()).getPermissions();
        if (CollectionUtils.isEmpty(permissionDTOS)) {
            return Collections.emptySet();
        }

        return permissionDTOS.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
            .map(PermissionDTO::getPermission).collect(Collectors.toSet());
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除菜单")
    @PreAuthorize("hasAuthority('sys:menu:del')")
    public void delete(@PathVariable Long id) {
        permissionService.delete(id);
    }
}
