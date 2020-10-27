package cn.bestsort.service;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:31
 */
public interface FileMappingService extends BaseService<FileMapping, Long> {
    /**
     * 获取用户文件列表
     * @param dirId  文件夹id
     * @param userId 用户id
     * @param status 状态
     * @param onlyDir 只列出文件夹
     * @return       列表集合
     */
    Page<FileMapping> listUserFiles(Pageable pageable, Long dirId, Long userId, Status status, Boolean onlyDir);

    /**
     * 只根据pid获取用户文件列表，仅用于文件分享查看
     * @param pid parent id
     * @return 文件列表
     */
    List<FileMapping> listFiles(Long pid);
    List<FileMapping> listUserFilesWithoutPage(Long dirId, Long userId, Status status, Boolean onlyDir);

    String fullPath(Long dirId);

}
