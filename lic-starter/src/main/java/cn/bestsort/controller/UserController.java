package cn.bestsort.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import cn.bestsort.model.dto.RetrievePasswordDTO;
import cn.bestsort.model.dto.UserDTO;
import cn.bestsort.model.dto.UserRegisterDTO;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.enums.InitStep;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.service.UserService;
import cn.bestsort.util.UserUtil;
import cn.bestsort.util.page.PageTableHandler;
import cn.bestsort.util.page.PageTableRequest;
import cn.bestsort.util.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Api(tags = "用户")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    MetaInfoService metaInfoService;

    @PostMapping
    @ApiOperation(value = "保存用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public User saveUser(@RequestBody @Valid UserDTO userDto) {
        userDto.setInfiniteCapacity(false);
        return userService.saveUser(userDto);
    }


    @PostMapping("/register")
    @ApiOperation(value = "注册用户")
    public User registerUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        UserDTO userDto = new UserDTO();
        List<Long> list = new ArrayList<>();
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        BeanUtils.copyProperties(user, userDto);
        String meta = metaInfoService.getMeta(LicMetaEnum.INIT_STATUS);
        if (InitStep.STEP_1.getKey().equals(meta)) {
            // admin无限容量
            userDto.setInfiniteCapacity(true);
            userDto.setTotalCapacity(-1);
            list.add((long) 1);
            userDto.setRoleIds(list);
            if (!userRegisterDTO.getAddress().isEmpty()) {
                metaInfoService.updateMeta(LicMetaEnum.HOST, userRegisterDTO.getAddress());
            } else {
                metaInfoService.updateMeta(LicMetaEnum.HOST, LicMetaEnum.HOST.getDefault());
            }
            metaInfoService.updateMeta(LicMetaEnum.INIT_STATUS, InitStep.STEP_2.getKey());
        } else if (InitStep.FINISH.getKey().equals(meta)) {
            userDto.setInfiniteCapacity(false);
            userDto.setTotalCapacity(1024);
            list.add((long) 2);
            userDto.setRoleIds(list);
        }
        return userService.saveUser(userDto);
    }

    @PutMapping
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public User updateUser(@RequestBody @Valid UserDTO userDto) {
        return userService.updateUser(userDto);
    }

    @PutMapping("/updateMyself")
    @ApiOperation(value = "修改用户自身")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public User updateMyself(@RequestBody @Valid User user) {
        return userService.update(user, user.getId());
    }

    @PutMapping("/{username}")
    @ApiOperation(value = "修改密码")
    @PreAuthorize("hasAuthority('sys:user:password')")
    public void changePassword(@PathVariable String username, String oldPassword, String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @PostMapping("/retrievePassword")
    @ApiOperation(value = "找回密码")
    public void retrievePassword(@RequestBody @Valid RetrievePasswordDTO retrievePasswordDTO) {
        userService.retrievePassword(retrievePasswordDTO);
    }

    @GetMapping
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public PageTableResponse listUsers(PageTableRequest request) {
        return PageTableHandler.handlePage(request, userService);
    }

    @ApiOperation(value = "根据用户id获取用户")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public User user(@PathVariable Long id) {
        return userService.getById(id);
    }

    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public User currentUser() {
        LoginUserVO userVO = UserUtil.getLoginUser();
        assert userVO != null;
        User user = userService.getById(userVO.getId());
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }


    /*@PutMapping(params = "headImgUrl")
    @ApiOperation(value = "修改头像")
    public void updateHeadImgUrl(String headImgUrl) {
        User user = UserUtil.getLoginUser();
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(user, userDto);
        userDto.setHeadImgUrl(headImgUrl);

        userService.updateUser(userDto);
        log.debug("{}修改了头像", user.getUsername());
    }*/
}
