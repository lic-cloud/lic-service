package cn.bestsort.controller;

import antlr.NameSpace;
import cn.bestsort.model.entity.User;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-07 10:38
 */

@Api(tags = "文件操作")
@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    LicFileManager fileManager;

    @Autowired
    FileManagerHandler handler;

    @ApiOperation(value = "获取文件下载链接")
    @GetMapping("/download")
    public String download(@RequestParam(required = false, defaultValue = "6000") Long expire,
                           @RequestParam(required = true) Long mappingId,
                           @RequestParam(required = true) NameSpace nameSpace) {
        User user = UserUtil.getLoginUser();
        String res = fileManager.createDownloadLink(mappingId, user, expire);
        return res;
    }
}
