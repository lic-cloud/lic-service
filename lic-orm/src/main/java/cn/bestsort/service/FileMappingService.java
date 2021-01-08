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
     * 为目标文件所有父文件夹修改容量，以达到容量正常显示的效果
     * @param pid 目标id
     * @param size KB
     */
    void changeSize4Parents(Long pid, Float size);
    /**
     * 获取用户文件列表
     * @param dirId  文件夹id
     * @param status 状态
     * @param onlyDir 只列出文件夹
     * @return       列表集合
     */
    Page<FileMapping> listUserFiles(Pageable pageable, Long dirId,Status status, Boolean onlyDir);

    Page<FileMapping> listUserFiles(Pageable page, Long userId, Long pid, Status status, Boolean onlyDir);

    /**
     * 只根据pid获取用户文件列表，仅用于文件分享查看
     * @param pid parent id
     * @return 文件列表
     */
    List<FileMapping> listFiles(Long pid);
    List<FileMapping> listUserFilesWithoutPage(Long dirId, Status status, Boolean onlyDir);

    FileMapping getMapping(Long mappingId, Status status, boolean isShare);
    String fullPath(Long dirId);

    /**
     * 移动/复制 文件/文件夹
     * @param isCopy true: 复制, false: 移动
     * @param mappingId 要移动的文件ID
     * @param targetDirPid 目标文件夹ID
     */
    void moveMapping(Boolean isCopy, Long mappingId, Long targetDirPid);

    /**
     * 容量检查, 单位一律为KB
     * @return 是否已超限制
     */
    Boolean checkCapPass(float add);

    Boolean checkCapPass();

    void rename(String name, Long id);
}
