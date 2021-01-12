package cn.bestsort.service;

import java.util.List;
import java.util.Map;

import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.vo.UploadTokenVO;

/**
 * 文件系统接口, 对外提供通用的接口实现
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 11:04
 */
public interface FileManager {
    String DOWNLOAD_LINK_PATH = "file/download";

    /**
     * 根据映射地址获取临时下载地址以供下载
     * @param path 映射地址
     * @param expire  链接过期时间, 单位为秒, -1为永不过期(P.S: 会导致链接常驻内存, 慎用)
     * @return  文件真实地址, 格式为{FileNamespace}::{realPath}
     */
    String downloadLink(String path, Long expire);

    /**
     * 根据参数生成不同的host和extConfig(不同实现支持的参数不同, 故采用Map)
     * @param config 参数配置
     * @return VO
     */
    UploadTokenVO generatorUploadVO(Map<String, String> config);

    /**
     * 实体删除的文件列表
     * @param fileDTO 文件入参
     */
    void delEntity(List<FileInfo> fileDTO);


    boolean match(FileNamespace namespace);
}
