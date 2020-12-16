package cn.bestsort.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.enums.file.LocalHostMetaEnum;
import cn.bestsort.model.param.UploadSuccessCallbackParam;
import cn.bestsort.model.vo.FileUploadVO;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.LocalUploadService;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.util.FileUtil;
import cn.bestsort.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "地址回调")
    @PostMapping("/check/{pid}")
    public ResponseEntity<FileUploadVO> check(@ApiParam(value = "文件总大小")
                                                  @RequestParam(name = "size") Float totalSize,
                                              @RequestParam(name = "name") String name,
                                              @PathVariable Long pid) {
        totalSize /= 1000;
        if (!mappingService.checkCapPass(totalSize)) {
            return FileUploadVO.overLimit();
        }
        if (pid == 0) {
            return FileUploadVO.ok(0);
        }
        List<FileMapping> fileMappings =
            mappingService.listUserFilesWithoutPage(pid, Status.VALID, false);
        for (FileMapping fileMapping : fileMappings) {
            if (fileMapping.getFileName().equals(name)) {
                return FileUploadVO.exist();
            }
        }
        return FileUploadVO.ok(null);
    }


    @SuppressWarnings("checkstyle:WhitespaceAround")
    @ApiOperation(value = "LOCALHOST文件上传")
    @PostMapping("/upload/{pid}")
    public ResponseEntity<Boolean> upload(@RequestParam(name = "name") String name,
                                          @RequestParam(name = "md5") String md5,
                                          @ApiParam(value = "文件总大小") @RequestParam(name = "size") Long totalSize,
                                          @RequestParam(defaultValue = "0", name = "total") Integer chunks,
                                          @RequestParam(defaultValue = "0", name = "index") Integer chunk,
                                          @PathVariable("pid") Long pid,
                                          @RequestParam MultipartFile data) throws IOException {
        // 是否可以进行秒传
        boolean superUpload = licFileManager.existMd5(md5, FileNamespace.LOCALHOST);
        UserUtil.mustGetLoginUser();
        boolean finished = superUpload;
        String fileName = name;
        if (!finished) {
            String random = service.getRandomFileName(md5, chunks, name);
            // 前端入参索引从1开始, 所以-1

            String path = FileUtil.unionPath(metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH),
                                             metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.DATA_DIR),
                                             random);
            File fileInfo = new File(path);
            finished = service.uploadWithBlock(md5, totalSize, chunks, chunk, data, fileInfo);
            if (finished) {
                File rename;
                do {
                    rename = new File(
                        FileUtil.unionPath(
                            metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH),
                            metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.DATA_DIR),
                            name + "_" + RandomStringUtils.randomAlphabetic(3)));
                } while (rename.exists() || rename.isDirectory());
                fileInfo.renameTo(rename);
                fileName = rename.getName();
            }
        }
        if (finished) {
            // byte -> kb
            UploadSuccessCallbackParam param = new UploadSuccessCallbackParam(
                (float)totalSize, name, pid, md5, FileNamespace.LOCALHOST,
                superUpload, fileName);
            // 文件系统实体同一目录下不可出现同名文件
            licFileManager.uploadSuccess(param);
            if (superUpload) {
                log.info("秒传成功， 文件名:{}, chunk: {}", name, chunk);
            }
        }
        return ResponseEntity.ok(superUpload);
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
                             HttpServletResponse response,
                             HttpServletRequest request) {
        if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }


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
