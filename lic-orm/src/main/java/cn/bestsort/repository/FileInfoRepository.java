package cn.bestsort.repository;

import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 19:07
 */
public interface FileInfoRepository extends BaseRepository<FileInfo, Long> {
    FileInfo getFirstByMd5AndNamespace(String md5, FileNamespace namespace);
}
