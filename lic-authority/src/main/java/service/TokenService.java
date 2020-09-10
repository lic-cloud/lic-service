package service;


import dto.LoginUser;
import dto.Token;

/**
 * Token管理器<br>
 * 可存储到redis或者数据库<br>
 * 具体可看实现类<br>
 * 默认基于redis，实现类为 com.boot.security.server.service.impl.TokenServiceJWTImpl<br>
 * 如要换成数据库存储，将TokenServiceImpl类上的注解@Primary挪到
 * com.boot.security.server.service.impl.TokenServiceDbImpl
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
