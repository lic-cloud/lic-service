package cn.bestsort.service;

import cn.bestsort.model.entity.FileShare;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:32
 */
public interface FileShareService extends BaseService<FileShare, Long> {
    boolean existsByUrl(String url);
}
