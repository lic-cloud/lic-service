package cn.bestsort.authority.service;


import cn.bestsort.authority.dto.LoginUser;
import cn.bestsort.authority.dto.Token;

/**
 * Token管理器
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface TokenService {
	//保存token
	Token saveToken(LoginUser loginUser);
	//刷新token
	void refresh(LoginUser loginUser);
	//根据token查找用户
	LoginUser getLoginUser(String token);
	//删除token
	boolean deleteToken(String token);

}
