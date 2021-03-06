package cn.bestsort.service;

import java.util.Map;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.param.UploadSuccessCallbackParam;
import cn.bestsort.model.vo.UploadTokenVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用于维护 lic 本身的文件系统， 该类主要操作 {@link FileMapping} 对应的数据库
 * @author bestsort
 * @version 1.0
 * @date 2020-09-07 17:33
 */
public interface LicFileManager {
    /**
     * 是否能够秒传
     * @param md5 校验和
     * @param fileNamespace 对应存储空间
     * @return 是否已经存在对应文件实体
     */
    boolean existMd5(String md5, FileNamespace fileNamespace);


    /**
     * 逻辑删除文件
     * @param fileId    文件映射 Id
     * @param remove    是否从回收站移除
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteFile(Long fileId, boolean remove);

    /**
     * 创建链接以供下载使用， 链接有效期为 expire(单位: S)
     * @param mappingId 需要下载的文件id
     * @param expire 链接过期时间
     * @return       生成后的链接
     */
    String createDownloadLink(Long mappingId, Long expire);

    /**
     * 创建上传所需要的token和相关参数
     * @param namespace 对应的文件系统实现
     * @param config    额外配置，由对应文件系统自行处理
     * @return          VO
     */
    UploadTokenVO createUploadToken(FileNamespace namespace, Map<String, String> config);


    /**
     * 上传成功， 数据库插入对应数据
     * @param param     相关参数
     */
    @Transactional(rollbackFor = Exception.class)
    void uploadSuccess(UploadSuccessCallbackParam param);

    /**
     * 创建文件映射
     * @param mapping 对应映射
     */
    void createMapping(FileMapping mapping);
}
