package cn.bestsort.controller;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.ShareManager;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-07 10:39
 */

@Api(tags = "分享")
@Controller("/share")
public class ShareController {
    @Autowired
    LicFileManager fileManager;

    @Autowired
    ShareManager shareManager;
    @GetMapping("/{url}/")
    public List<FileMapping> listShare(@PathVariable String url,
                                       @RequestParam(required = false) Long pid) {
        return shareManager.listFilesByShare(url, pid);
    }

}
