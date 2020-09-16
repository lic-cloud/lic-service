package cn.bestsort.authority.service.impl;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.alibaba.fastjson.JSON;

import cn.bestsort.authority.dto.LoginUser;
import cn.bestsort.authority.dto.Token;
import cn.bestsort.authority.service.TokenService;
import cn.bestsort.cache.CacheHandler;
import cn.bestsort.constant.CachePrefix;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.service.impl.MetaInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * token存到redis的实现类
 *
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Slf4j
@Primary
@Service
public class TokenServiceJWTImpl implements TokenService {


    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired
    private CacheHandler cacheHandler;

    /**
     * 私钥：随便字符串
     */
    @Value("${token.jwtSecret}")
    private String jwtSecret;

    private static Key KEY = null;

    @Override
    public Token saveToken(LoginUser loginUser) {
        loginUser.setToken(UUID.randomUUID().toString());
        cacheLoginUser(loginUser);

        String jwtToken = createToken(loginUser);

        return new Token(jwtToken, loginUser.getLoginTime());
    }

    /**
     * 生成jwt
     */
    private String createToken(LoginUser loginUser) {
        Map<String, Object> claims = new HashMap<>();
		// 放入一个随机字符串，通过该串可找到登陆用户
        claims.put(CachePrefix.LOGIN_USER_KEY, loginUser.getToken());

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
            .compact();
    }

    private void cacheLoginUser(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        long expire = Long.parseLong(metaInfoService.getMetaOrDefault(LicMetaEnum.CACHE_EXPIRE)) * 100;
        loginUser.setExpireTime(loginUser.getLoginTime());
        // 根据uuid将loginUser缓存
        cacheHandler.fetchCacheStore().put(getTokenKey(loginUser.getToken()), JSON.toJSONString(loginUser),
                                           expire, TimeUnit.MINUTES);
    }

    /**
     * 更新缓存的用户信息
     */
    @Override
    public void refresh(LoginUser loginUser) {
        cacheLoginUser(loginUser);
    }

    @Override
    public LoginUser getLoginUser(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            return cacheHandler.fetchCacheStore().getObj(LoginUser.class, getTokenKey(uuid));
        }

        return null;
    }

    @Override
    public boolean deleteToken(String jwtToken) {
        String uuid = getUUIDFromJWT(jwtToken);
        if (uuid != null) {
            String key = getTokenKey(uuid);
            LoginUser loginUser = cacheHandler.fetchCacheStore().getObj(LoginUser.class, key);
            if (loginUser != null) {
                cacheHandler.fetchCacheStore().delete(key);
                return true;
            }
        }

        return false;
    }

    private String getTokenKey(String uuid) {
        return "tokens:" + uuid;
    }

    private Key getKeyInstance() {
        if (KEY == null) {
            synchronized (TokenServiceJWTImpl.class) {
                if (KEY == null) {
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }

        return KEY;
    }

    private String getUUIDFromJWT(String jwtToken) {
        if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
            return null;
        }

        try {
            Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken).getBody();
            return MapUtils.getString(jwtClaims, CachePrefix.LOGIN_USER_KEY);
        } catch (ExpiredJwtException e) {
            log.error("{}已过期", jwtToken);
        } catch (Exception e) {
            log.error("token: {}", jwtToken, e);
        }

        return null;
    }
}
