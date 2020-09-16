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
	/**
	 * 保存token
	 * @param loginUser
	 * @return
	 */
	Token saveToken(LoginUser loginUser);

	/**
	 * 刷新token
	 * @param loginUser
	 */
	void refresh(LoginUser loginUser);

	/**
	 * 根据token查找用户
	 * @param token
	 * @return
	 */
	LoginUser getLoginUser(String token);

	/**
	 * 删除token
	 * @param token
	 * @return
	 */
	boolean deleteToken(String token);

}
