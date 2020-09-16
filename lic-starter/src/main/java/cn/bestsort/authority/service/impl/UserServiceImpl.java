package cn.bestsort.authority.service.impl;

import cn.bestsort.authority.dao.UserDao;
import cn.bestsort.authority.dto.UserDto;
import cn.bestsort.authority.model.User;
import cn.bestsort.authority.model.User.Status;
import cn.bestsort.authority.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User saveUser(UserDto userDto) {
        User user = userDto;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.VALID);
        userDao.save(user);
        saveUserRoles(user.getId(), userDto.getRoleIds());

        log.debug("新增用户{}", user.getUsername());
        return user;
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds != null) {
            userDao.deleteUserRole(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                userDao.saveUserRoles(userId, roleIds);
            }
        }
    }

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User u = userDao.getUser(username);
        if (u == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }

        userDao.changePassword(u.getId(), passwordEncoder.encode(newPassword));

        log.debug("修改{}的密码", username);
    }

    @Override
    @Transactional
    public User updateUser(UserDto userDto) {
        userDao.update(userDto);
        saveUserRoles(userDto.getId(), userDto.getRoleIds());

        return userDto;
    }

}
