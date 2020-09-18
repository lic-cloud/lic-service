package cn.bestsort.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Controller
public class LoginPageConfig {

    @RequestMapping("/")
    public RedirectView loginPage() {
        return new RedirectView("/login.html");
    }
}
