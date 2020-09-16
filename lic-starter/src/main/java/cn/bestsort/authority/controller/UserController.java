package cn.bestsort.authority.controller;

import java.util.List;

import cn.bestsort.authority.dao.UserDao;
import cn.bestsort.authority.dto.UserDto;
import cn.bestsort.authority.model.User;
import cn.bestsort.authority.page.table.PageTableHandler;
import cn.bestsort.authority.page.table.PageTableHandler.CountHandler;
import cn.bestsort.authority.page.table.PageTableHandler.ListHandler;
import cn.bestsort.authority.page.table.PageTableRequest;
import cn.bestsort.authority.page.table.PageTableResponse;
import cn.bestsort.authority.service.UserService;
import cn.bestsort.authority.utils.UserUtil;
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
    private UserDao userDao;


    @PostMapping
    @ApiOperation(value = "保存用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public User saveUser(@RequestBody UserDto userDto) {
        User u = userService.getUser(userDto.getUsername());
        if (u != null) {
            throw new IllegalArgumentException(userDto.getUsername() + "已存在");
        }

        return userService.saveUser(userDto);
    }


    @PutMapping
    @ApiOperation(value = "修改用户")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public User updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }


    @PutMapping(params = "headImgUrl")
    @ApiOperation(value = "修改头像")
    public void updateHeadImgUrl(String headImgUrl) {
        User user = UserUtil.getLoginUser();
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setHeadImgUrl(headImgUrl);

        userService.updateUser(userDto);
        log.debug("{}修改了头像", user.getUsername());
    }


    @PutMapping("/{username}")
    @ApiOperation(value = "修改密码")
    @PreAuthorize("hasAuthority('sys:user:password')")
    public void changePassword(@PathVariable String username, String oldPassword, String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @GetMapping
    @ApiOperation(value = "用户列表")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public PageTableResponse listUsers(PageTableRequest request) {
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return userDao.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<User> list(PageTableRequest request) {
                List<User> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
                return list;
            }
        }).handle(request);
    }

    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public User currentUser() {
        return UserUtil.getLoginUser();
    }

    @ApiOperation(value = "根据用户id获取用户")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:user:query')")
    public User user(@PathVariable Long id) {
        return userDao.getById(id);
    }

}
