package cn.bestsort.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/10 9:21
 */
@RestController
public class test {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
