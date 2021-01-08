package cn.bestsort.util;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.vo.LoginUserVO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class UserUtil {

    public static void checkIsOwner(Long ownerId) {
        if (!mustGetLoginUser().getId().equals(ownerId)) {
            throw ExceptionConstant.UNAUTHORIZED;
        }
    }

    public static Long getLoginUserId() {
        return mustGetLoginUser().getId();
    }

    public static LoginUserVO getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return (LoginUserVO) authentication.getPrincipal();
            }
        }
        return null;
    }

    public static LoginUserVO mustGetLoginUser() {
        LoginUserVO res = getLoginUser();
        if (res == null) {
            throw ExceptionConstant.UNAUTHORIZED;
        }
        return res;
    }
}
