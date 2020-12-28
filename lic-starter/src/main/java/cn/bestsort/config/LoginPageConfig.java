package cn.bestsort.config;

import cn.bestsort.model.enums.InitStep;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.service.MetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Controller
public class LoginPageConfig {
    @Autowired
    MetaInfoService metaInfoService;

    @RequestMapping("/")
    public String loginPage() {
        String meta = metaInfoService.getMetaOrDefaultStr(LicMetaEnum.INIT_STATUS);
        if (!InitStep.FINISH.getKey().equals(meta)) {
            return "/init.html";
        }
        return "/login.html";
    }
}
