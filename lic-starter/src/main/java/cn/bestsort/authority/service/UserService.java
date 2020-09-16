package cn.bestsort.authority.service;


import cn.bestsort.authority.dto.UserDto;
import cn.bestsort.authority.model.User;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface UserService {

	User saveUser(UserDto userDto);

	User updateUser(UserDto userDto);

	User getUser(String username);

	void changePassword(String username, String oldPassword, String newPassword);

}
