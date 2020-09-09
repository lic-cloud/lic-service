package cn.bestsort.service.impl;

import java.util.Optional;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.entity.user.User;
import cn.bestsort.repository.UserRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 11:10
 */

@Slf4j
@Service
public class UserServiceImpl extends AbstractBaseService<User, Long> implements UserService {

    @Override
    public User login(String rememberToken, String requestIp) {
        DecodedJWT jwtUnVerify;
        try {
            jwtUnVerify = JWT.decode(rememberToken);
        } catch (JWTDecodeException ignore) {
            return null;
        }
        Optional<User> optional = userRepo.findByUserName(jwtUnVerify.getClaim("username").asString());
        if (optional.isEmpty()) {
            return null;
        }
        User        entity    = optional.get();
        Algorithm   algorithm = Algorithm.HMAC512(entity.getUserPassword() + entity.getRememberToken());
        JWTVerifier verifier  = JWT.require(algorithm).build();
        try {
            verifier.verify(rememberToken);
        } catch (JWTVerificationException e) {
            log.info(String.format("well, someone try to act as %s, IP is %s", entity.getUserName(), requestIp));
            return null;
        }
        return isLocked(entity);
    }

    private User isLocked(User user) {
        if (user.getStatus().equals(Status.INVALID)) {
            throw ExceptionConstant.USER_HAS_BEEN_LOCKED;
        }
        return user;
    }

    final UserRepository userRepo;
    protected UserServiceImpl(UserRepository repository) {
        super(repository);
        this.userRepo = repository;
    }

}
