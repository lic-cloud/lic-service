package cn.bestsort.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import cn.bestsort.model.entity.user.User;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.param.UploadSuccessCallbackParam;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.LocalUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:19
 */
@RestController
@RequestMapping("/upload")
public class LocalHostUploaderController {
    @Autowired
    LocalUploadService service;
    @Autowired
    LicFileManager licFileManager;
    @PostMapping("/bigFile")
    public ResponseEntity<Boolean> upload(String name, String md5, Long size,
                                          Integer chunks, Integer chunk,
                                          Long pid, MultipartFile file,
                                          HttpServletRequest request) throws IOException {

        // 是否可以进行秒传
        boolean finished = licFileManager.canSuperUpload(md5, FileNamespace.LOCALHOST);
        User user = (User)request.getSession().getAttribute(LicMetaEnum.USER_SESSION.getVal());
        if (!finished) {
            if (chunks != null && chunks != 0) {
                finished = service.uploadWithBlock(md5, size, chunks, chunk, file);
            } else {
                finished = service.simpleUpload(file);
            }
        }
        UploadSuccessCallbackParam param = new UploadSuccessCallbackParam();
        param.setName(name);
        // TODO 文件系统实体同一目录下不可出现同名文件
        param.setMd5(md5);
        param.setNamespace(FileNamespace.LOCALHOST);
        param.setPid(pid);
        // byte -> kb
        param.setSize((float)(file.getSize() / 1000));
        User user1 = new User();
        user1.setUsername("name");
        user1.setId(1L);
        licFileManager.uploadSuccess(user1, param);
        return ResponseEntity.ok(finished);
    }
}
