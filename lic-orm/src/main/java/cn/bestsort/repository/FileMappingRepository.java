package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:39
 */
public interface FileMappingRepository extends BaseRepository<FileMapping, Long> {

    /**
     * 只获取用户当前目录下文件/文件夹
     */
    List<FileMapping> findAllByPidAndOwnerIdAndStatusAndIsDir(Long pid, Long userId, Status status, Boolean isDir);
    List<FileMapping> findAllByPidAndOwnerIdAndStatus(Long pid, Long userId, Status status);
    Page<FileMapping> findAllByPidAndOwnerIdAndStatusAndIsDir(Pageable page, Long pid, Long userId, Status status, Boolean isDir);

}
