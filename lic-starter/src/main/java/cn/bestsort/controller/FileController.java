package cn.bestsort.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    CacheHandler cache;

    @ApiOperation("获取文件列表")
    @GetMapping
    @ResponseBody
    public List<FileMapping> list(@RequestParam(defaultValue = "VALID") Status status) {
        return fileManager.listFiles(0L, 1L, status);
    }
    @ApiOperation(value = "获取文件下载链接", notes = "默认链接有效期12小时")
    @GetMapping("/download")
    @ResponseBody
    public String download(@RequestParam(required = false, defaultValue = "43200") Long expire,
                           @RequestParam Long mappingId) {

        return fileManager.createDownloadLink(mappingId, UserUtil.getLoginUser(), expire);
    }

    @ApiOperation(value = "LOCALHOST文件下载", notes = "默认链接有效期12小时")
    @GetMapping("/download/server")
    @ResponseBody
    public void downloadFile(@RequestParam(required = true) String key,
                             @RequestParam(required = false) String fileName,
                             HttpServletResponse response) throws IOException {
        String path = cache.fetchCacheStore().get(key);
        if (path == null) {
            throw ExceptionConstant.NOT_FOUND_ITEM;
        }
        File file = new File(path);
        if (!file.exists()) {
            throw ExceptionConstant.NOT_FOUND_SUCH_FILE;
        }
        response.setContentType("application/force-download");
        if (fileName != null) {
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        }
        byte[] buffer = new byte[1024];

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        OutputStream outputStream = response.getOutputStream();
        int i = bis.read(buffer);
        while (i != -1) {
            outputStream.write(buffer, 0, i);
            i = bis.read(buffer);
        }
        try {
            bis.close();
        } catch (IOException ignore) { }
        try {
            fis.close();
        } catch (IOException ignore) { }
    }

}
