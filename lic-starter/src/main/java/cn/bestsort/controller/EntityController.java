package cn.bestsort.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.param.UploadSuccessCallbackParam;
import cn.bestsort.model.vo.LoginUserVO;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.LocalUploadService;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * LOCALHOST 下载、上传相关处理
 * @author bestsort
 * @version 1.0
 * @date 2020-10-18 09:45
 */
@Slf4j
@Controller
@Api(tags = "文件实体")
@RequestMapping("/file")
public class EntityController {
    @Autowired
    LicFileManager     fileManager;
    @Autowired
    FileMappingService mappingService;
    @Autowired
    FileManagerHandler handler;
    @Autowired
    CacheHandler       cache;
    @Autowired
    LocalUploadService service;
    @Autowired
    LicFileManager     licFileManager;
    @Autowired
    MetaInfoService metaInfoService;
    @ApiOperation(value = "Resource文件上传")
    @PostMapping("/resources")
    public ResponseEntity<Boolean> upload(MultipartFile file) throws IOException {
        service.uploadResource(file);
        return ResponseEntity.ok(Boolean.TRUE);
    }
    @ApiOperation(value = "LOCALHOST文件上传")
    @PostMapping("/upload")
    public ResponseEntity<Boolean> upload(@RequestParam String name,
                                          @RequestParam String md5,
                                          @ApiParam(value = "文件总大小") @RequestParam Long totalSize,
                                          @RequestParam(defaultValue = "0") Integer chunks,
                                          @RequestParam(defaultValue = "0") Integer chunk,
                                          @RequestParam(defaultValue = "0") Long pid,
                                          @RequestParam MultipartFile file) throws IOException {
        // 是否可以进行秒传
        boolean finished = licFileManager.canSuperUpload(md5, FileNamespace.LOCALHOST);
        LoginUserVO user = UserUtil.getLoginUser();
        if (!finished) {
            if (chunks != null && chunks != 0) {
                finished = service.uploadWithBlock(md5, totalSize, chunks, chunk, file);
            } else {
                finished = service.simpleUpload(file);
            }
        }
        // byte -> kb
        UploadSuccessCallbackParam param = new UploadSuccessCallbackParam(
            (float) (file.getSize() / 1000), name, pid, md5, FileNamespace.LOCALHOST);
        // TODO 文件系统实体同一目录下不可出现同名文件
        licFileManager.uploadSuccess(param);
        return ResponseEntity.ok(finished);
    }


    @ApiOperation(value = "获取文件下载链接", notes = "先获取文件下载链接，再根据链接去发送文件下载请求下载到本地")
    @GetMapping("/download")
    @ResponseBody
    public String download(@ApiParam(value = "链接过期时间,单位(S),默认有效期12小时")
                               @RequestParam(required = false, defaultValue = "43200") Long expire,
                           @RequestParam Long mappingId) {

        return fileManager.createDownloadLink(mappingId, expire);
    }

    @ApiOperation(value = "LOCALHOST文件下载", notes = "默认链接有效期12小时")
    @GetMapping("/download/server")
    @ResponseBody
    public void downloadFile(@RequestParam String key,
                             @RequestParam(required = false) String fileName,
                             HttpServletResponse response) {
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
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream outputStream = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (IOException e) {
            log.error("[LOCALHOST] file download error: ", e);
        }
    }
}
