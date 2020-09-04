package cn.bestsort.service;

import cn.bestsort.enums.FileNamespace;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:36
 */
public interface FileManagerHandler {
    /**
     * 根据namespace获取对应的实现
     * @param namespace enum
     * @return  对应实现
     */
    FileManager handle(FileNamespace namespace);
}
