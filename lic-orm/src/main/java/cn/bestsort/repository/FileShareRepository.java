package cn.bestsort.repository;

import cn.bestsort.model.FileShare;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:03
 */
public interface FileShareRepository extends BaseRepository<FileShare, Long> {
    Boolean existsByUrl(String url);
}
