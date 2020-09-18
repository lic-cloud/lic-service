package cn.bestsort.service;

import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.model.dto.TokenDTO;

/**
 * Token管理器
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public interface TokenService {
    /**
     * 保存token
     * @param loginUserVO
     * @return
     */
    TokenDTO saveToken(LoginUserVO loginUserVO);

    /**
     * 刷新token
     * @param loginUserVO
     */
    void refresh(LoginUserVO loginUserVO);

    /**
     * 根据token查找用户
     * @param token
     * @return
     */
    LoginUserVO getLoginUser(String token);

    /**
     * 删除token
     * @param token
     * @return
     */
    boolean deleteToken(String token);

}
