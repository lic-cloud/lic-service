package cn.bestsort.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-15 09:22
 */
public interface LocalUploadService {
    /**
     * 简单上传文件
     * @param file  文件实体
     * @throws IOException exception
     * @return 是否上传完毕
     */
    boolean simpleUpload(MultipartFile file) throws IOException;

    boolean uploadResource(MultipartFile file) throws IOException;

    /**
     * 分块上传文件(适用于大文件上传)
     * @param md5      当前文件块校验和
     * @param size     文件大小
     * @param chunks   文件块总数
     * @param chunk    当前文件块索引
     * @param file     文件块实体
     * @throws IOException  exception
     * @return 是否上传完毕
     */
    boolean uploadWithBlock(String md5, Long size,
                            Integer chunks, Integer chunk,
                            MultipartFile file) throws IOException;
}
