package cn.bestsort.service;

import cn.bestsort.model.dto.RetrievePasswordDTO;
import cn.bestsort.model.dto.UserDTO;
import cn.bestsort.model.entity.User;
import cn.bestsort.util.page.Listable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-09 11:09
 */
public interface UserService extends BaseService<User, Long>, Listable<User> {
    /**
     * 保存用户
     *
     * @param userDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    User saveUser(UserDTO userDto);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    User getUser(String username);

    /**
     * 根据用户名、手机号、邮箱获取用户
     *
     * @param username
     * @param phone
     * @param email
     * @return
     */
    StringBuffer getUsers(String username, String phone, String telephone, String email);

    /**
     * 修改密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(String username, String oldPassword, String newPassword);

    /**
     * 更新用户
     *
     * @param userDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    User updateUser(UserDTO userDto);

    /**
     * 找回密码
     *
     * @param retrievePasswordDTO
     */
    void retrievePassword(RetrievePasswordDTO retrievePasswordDTO);
}
