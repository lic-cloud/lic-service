package cn.bestsort.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import cn.bestsort.model.enums.file.LocalHostMetaEnum;
import cn.bestsort.repository.FileInfoRepository;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.LocalUploadService;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:22
 */
@Service
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

        String path = metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH) +
                metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.DATA_DIR) + file.getOriginalFilename();
        File localFile = new File(path);
        int cnt = 0;
        while (localFile.exists() || localFile.isDirectory()) {
            localFile = new File(path + "_" + (++cnt));
        }

        File dir = new File(localFile.getParent());
        // 防止因为文件夹未创建而造成的 IOException, 不 care 创建结果
        dir.mkdirs();
        FileUtil.write(localFile, file.getInputStream());
        return true;
    }

    @Override
    public boolean uploadWithBlock(String md5, Long size,
                                   Integer chunks, Integer chunk,
                                   MultipartFile file) throws IOException {
        String fileName = getFileName(md5, chunks);
        FileUtil.writeWithBlock(metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH) +
                                     metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.DATA_DIR) +
                                     fileName, size, file.getInputStream(), file.getSize(), chunks, chunk);
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
        return uploaded;
    }


    /**
     * 用来存储文件分块信息
     */
    private static class FileBlock {
        String name;
        boolean[] status;

        FileBlock(int n) {
            this.name = UUID.randomUUID().toString();
            this.status = new boolean[n];
        }
    }
    private String getFileName(String md5, int chunks) {
        FileBlock res = fileUploadBufferPool.get(md5);
        if (res == null) {
            res = new FileBlock(chunks);
            fileUploadBufferPool.put(md5, res);
        }
        return res.name;
    }
}
