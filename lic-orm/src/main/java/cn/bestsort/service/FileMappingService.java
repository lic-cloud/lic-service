package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:31
 */
public interface FileMappingService extends BaseService<FileMapping, Long> {
    /**
     * 获取用户文件列表
     * @param dirId 文件夹id
     * @param id    用户id
     * @return      列表集合
     */
    List<FileMapping> listUserFile(Long dirId, Long userId);

    String fullPath(Long dirId);

}
