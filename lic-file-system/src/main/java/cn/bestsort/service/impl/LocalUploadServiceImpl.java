package cn.bestsort.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.enums.file.LocalHostMetaEnum;
import cn.bestsort.repository.FileInfoRepository;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.LocalUploadService;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:22
 */
@Service
@Slf4j
public class LocalUploadServiceImpl implements LocalUploadService {
    @Autowired
    MetaInfoService metaInfoService;
    @Autowired
    LicFileManager  licFileManager;
    @Autowired
    FileInfoRepository fileInfoRepo;

    /**
     * [Md5, [RandomUUID, FileBlock]]
     */
    final ConcurrentHashMap<String, FileBlock> fileUploadBufferPool = new ConcurrentHashMap<>();

    @Override
    public boolean simpleUpload(MultipartFile file) throws IOException {
        String path = FileUtil.unionPath(
            metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH),
            file.getOriginalFilename()
        );
        checkPath(path, file);
        return true;
    }

    @Override
    public boolean uploadResource(MultipartFile file) throws IOException {
        String path = FileUtil.unionPath(
            metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH),
            metaInfoService.getMetaOrDefaultStr(LicMetaEnum.RESOURCE_DIR),
            RandomStringUtils.randomNumeric(
                metaInfoService.getMetaObj(Integer.class, LicMetaEnum.RESOURCE_DIR_TITLE_LENGTH)),
            file.getOriginalFilename());
        checkPath(path, file);

        return true;
    }

    private void checkPath(String path, MultipartFile file) throws IOException {
        File localFile = new File(path);
        int cnt = 0;
        while (localFile.exists() || localFile.isDirectory()) {
            localFile = new File(path + "_" + (++cnt));
        }

        File dir = new File(localFile.getParent());
        // 防止因为文件夹未创建而造成的 IOException, 不 care 创建结果
        dir.mkdirs();
        FileUtil.write(localFile, file.getInputStream());
        log.info("文件上传成功, 文件名:[{}], ；路径:[{}] ", file.getOriginalFilename(), path);
    }
    @Override
    public boolean uploadWithBlock(String md5, Long size,
                                   Integer chunks, Integer chunk,
                                   MultipartFile file,
                                   File target) throws IOException {

        chunk--;
        //TODO check
        FileUtil.writeWithBlock(target.getAbsolutePath(), size, file.getInputStream(), file.getSize(), chunks, chunk);
        // 标记当前文件块
        fileUploadBufferPool.get(md5).status[chunk] = true;
        boolean uploaded = true;
        for (boolean success : fileUploadBufferPool.get(md5).status) {
            if (!success) {
                uploaded = false;
                break;
            }
        }

        if (uploaded) {
            fileUploadBufferPool.remove(md5);
        }
        log.info("file:{} chunk [{}] uploaded, total: [{}]", target.getName(), chunk, chunks);
        return uploaded;
    }


    /**
     * 用来存储文件分块信息
     */
    private static class FileBlock {
        String    randomKey;
        boolean[] status;
        String originName;
        FileBlock(int n) {
            this.randomKey = UUID.randomUUID().toString();
            this.status    = new boolean[n];
        }

        public void setOriginName(String originName) {
            this.originName = originName;
        }
    }
    @Override
    public String getRandomFileName(String md5, int chunks, String fileName) {
        FileBlock res = fileUploadBufferPool.get(md5);
        if (res == null) {
            res = new FileBlock(chunks);
            fileUploadBufferPool.put(md5, res);
        }
        return res.randomKey;
    }
}
