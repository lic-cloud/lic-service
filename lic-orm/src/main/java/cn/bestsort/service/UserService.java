package cn.bestsort.service;

import cn.bestsort.model.dto.UserDTO;
import cn.bestsort.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 11:09
 */
public interface UserService extends BaseService<User, Long> {
    @Transactional
    User saveUser(UserDTO userDto);

    User getUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);

    @Transactional
    User updateUser(UserDTO userDto);
}
