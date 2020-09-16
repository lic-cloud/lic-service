package cn.bestsort.service;

import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:21
 */
public interface FileInfoService extends BaseService<FileInfo, Long> {
    /**
     * 根据 校验和 和 文件空间 获取文件信息
     * @param md5 md5
     * @param namespace 文件空间
     * @return 对应文件行记录
     */
    FileInfo getByMd5(String md5, FileNamespace namespace);
}
