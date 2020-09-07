package cn.bestsort.service;

import java.util.List;

import cn.bestsort.dto.FileDTO;
import cn.bestsort.entity.FileInfo;
import cn.bestsort.enums.FileNamespace;
import cn.bestsort.exception.LicException;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件系统接口, 对外提供通用的接口实现
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 11:04
 */
public interface FileManager {
    /**
     * 根据 Path 获取实际文件路径.
     * 公共文件的username恒为"share"
     * @see cn.bestsort.enums.FileNamespace
     * @param fileDTO 映射地址
     * @return  文件列表, 当且仅当path为目录时有效，否则返回空
     */
    List<FileInfo> list(@NonNull FileDTO fileDTO);

    /**
     * 根据映射地址获取真实地址以提供下载、删除等服务
     * @param fileDTO 映射地址
     * @return  文件真实地址, 格式为{FileNamespace}::{realPath}
     */
    String realDir(FileDTO fileDTO);

    /**
     * 当目标路径已存在对应文件的时候抛出{@link cn.bestsort.constant.ExceptionConstant#TARGET_EXIST}异常
     * @param fileDTO           文件入参
     * @param targetDirId       目标路径
     * @throws LicException     文件/文件名冲突
     */
    void move(FileDTO fileDTO, Long targetDirId) throws LicException;

    /**
     * TODO
     * @param fileDTO   入参
     * @param files     文件
     */
    void upload(FileDTO fileDTO, MultipartFile[] files);

    /**
     * 当目标文件名已存在时抛出TargetExistException异常
     * TODO
     * @param  fileDTO      文件入参
     * @param targetName    文件出参
     */
    void rename(FileDTO fileDTO, String targetName);

    /**
     * 实体删除文件
     * @param fileDTO 文件入参
     */
    void del(FileDTO fileDTO);

    boolean match(FileNamespace namespace);
}
