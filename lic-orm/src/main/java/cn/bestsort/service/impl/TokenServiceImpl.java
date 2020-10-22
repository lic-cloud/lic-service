package cn.bestsort.service.impl;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import com.alibaba.fastjson.JSON;
import cn.bestsort.cache.CacheHandler;
import cn.bestsort.constant.CachePrefix;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.model.dto.TokenDTO;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
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
public class TokenServiceImpl implements TokenService {


    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired
    private CacheHandler cacheHandler;

    /**
     * 私钥：随便字符串
     */
    @Value("${token.jwtSecret}")
    private String jwtSecret;
    /**
     * token过期秒数
     */
    @Value("${token.expire.seconds}")
    private Integer expireSeconds;

    private static Key KEY = null;

    @Override
    public TokenDTO saveToken(LoginUserVO loginUserVO) {
        loginUserVO.setToken(UUID.randomUUID().toString());
        cacheLoginUser(loginUserVO);
        String jwtToken = createToken(loginUserVO);
        return new TokenDTO(jwtToken, loginUserVO.getLoginTime());
    }

    /**
     * 生成jwt
     */
    private String createToken(LoginUserVO loginUserVO) {
        Map<String, Object> claims = new HashMap<>(4);
        // 放入一个随机字符串，通过该串可找到登陆用户
        claims.put(CachePrefix.LOGIN_USER_KEY, loginUserVO.getToken());
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
            .compact();
    }

    private void cacheLoginUser(LoginUserVO loginUserVO) {
        loginUserVO.setLoginTime(System.currentTimeMillis());
        long expire = metaInfoService.getMetaObj(Long.class, LicMetaEnum.CACHE_EXPIRE) * 60;
        loginUserVO.setExpireTime(loginUserVO.getLoginTime() + expireSeconds * 1000);
        // 根据uuid将loginUser缓存
        cacheHandler.fetchCacheStore().put(getTokenKey(loginUserVO.getToken()), JSON.toJSONString(loginUserVO),
            expire, TimeUnit.MINUTES);
    }

    /**
     * 更新缓存的用户信息
     */
    @Override
    public void refresh(LoginUserVO loginUserVO) {
        cacheLoginUser(loginUserVO);
    }

    @Override
    public LoginUserVO getLoginUser(String jwtToken) {
        String uuid = getRandomKey(jwtToken);
        if (uuid != null) {
            return cacheHandler.fetchCacheStore().getObj(LoginUserVO.class, getTokenKey(uuid));
        }
        return null;
    }

    @Override
    public boolean deleteToken(String jwtToken) {
        String uuid = getRandomKey(jwtToken);
        if (uuid != null) {
            String key = getTokenKey(uuid);
            LoginUserVO loginUserVO = cacheHandler.fetchCacheStore().getObj(LoginUserVO.class, key);
            if (loginUserVO != null) {
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
            synchronized (TokenServiceImpl.class) {
                if (KEY == null) {
                    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
                    KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
                }
            }
        }
        return KEY;
    }
    private String getRandomKey(String jwtToken) {
        String isNull = "null";
        if (isNull.equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
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
