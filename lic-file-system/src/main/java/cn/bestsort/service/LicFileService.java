package cn.bestsort.service;

import java.util.List;

import cn.bestsort.entity.FileMapping;
import cn.bestsort.entity.user.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于维护 lic 本身的文件系统， 该类主要操作 {@link cn.bestsort.entity.FileMapping} 对应的数据库
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:33
 */
public interface LicFileService {

    List<FileMapping> listFiles(Long dirId, User user);

    /**
     * 逻辑删除文件
     * @param fileId    文件映射 Id
     * @param user      对应用户
     * @param remove    是否从回收站移除
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteFile(Long fileId, User user, boolean remove);
}
