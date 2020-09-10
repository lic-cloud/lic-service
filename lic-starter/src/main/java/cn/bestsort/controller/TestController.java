package cn.bestsort.controller;

import cn.bestsort.model.vo.UserVo;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 20:26
 */

@Api
@RestController("/api/login")
public class TestController {

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<UserVo> login() {
        return ResponseEntity.ok(new UserVo("username",1, "token"));
    }
}
