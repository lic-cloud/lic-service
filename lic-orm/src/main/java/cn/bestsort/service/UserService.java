package cn.bestsort.service;

import cn.bestsort.model.dto.UserDTO;
import cn.bestsort.model.entity.User;
import cn.bestsort.util.page.Listable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 11:09
 */
public interface UserService extends BaseService<User, Long>, Listable<User> {
    @Transactional(rollbackFor = Exception.class)
    User saveUser(UserDTO userDto);

    User getUser(String username);

    void changePassword(String username, String oldPassword, String newPassword);

    @Transactional(rollbackFor = Exception.class)
    User updateUser(UserDTO userDto);

}
