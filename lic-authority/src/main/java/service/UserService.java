package service;

import cn.bestsort.model.entity.user.User;
import dto.UserDto;

public interface UserService {

    User saveUser(UserDto userDto);

    User updateUser(UserDto userDto);

    void changePassword(String username, String oldPassword, String newPassword);

}
