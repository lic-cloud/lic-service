package cn.bestsort.config;

import cn.bestsort.model.entity.User;
import cn.bestsort.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Controller
public class LoginPageConfig {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/")
    public RedirectView loginPage() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return new RedirectView("/init.html");
        } else {
            return new RedirectView("/login.html");
        }
    }
}
