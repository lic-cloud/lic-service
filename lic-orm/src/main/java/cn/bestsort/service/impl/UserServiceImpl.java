package cn.bestsort.service.impl;

import java.util.LinkedList;
import java.util.List;

import cn.bestsort.model.dto.UserDTO;
import cn.bestsort.model.entity.RoleUser;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.entity.User.Status;
import cn.bestsort.repository.UserRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.RoleUserService;
import cn.bestsort.service.UserService;
import cn.bestsort.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Service
public class UserServiceImpl extends AbstractBaseService<User, Long> implements UserService {

    @Autowired
    private RoleUserService roleUserService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public User saveUser(UserDTO userDto) {
        User user = new User();
        SpringUtil.cloneWithoutNullVal(userDto, user);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(Status.VALID);
        save(user);
        saveUserRoles(user.getId(), userDto.getRoleIds());
        log.debug("新增用户{}", user.getUsername());
        return user;
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds != null) {
            removeById(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                List<RoleUser> list = new LinkedList<>();
                roleIds.forEach(i -> list.add(RoleUser.of(userId, i)));
                roleUserService.saveAll(list);
            }
        }
    }

    @Override
    public User getUser(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User u = userRepo.findByUsername(username);
        if (u == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }
        u.setPassword(passwordEncoder.encode(newPassword));
        update(u, u.getId());
        log.debug("修改{}的密码", username);
    }

    @Override
    @Transactional
    public User updateUser(UserDTO userDto) {
        User user = new User();
        SpringUtil.cloneWithoutNullVal(userDto, user);
        update(user, user.getId());

        saveUserRoles(userDto.getId(), userDto.getRoleIds());

        return userDto;
    }

    protected UserServiceImpl(UserRepository repository) {
        super(repository);
        userRepo = repository;
    }
}
