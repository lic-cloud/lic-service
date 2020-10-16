package cn.bestsort.service;

import java.util.List;
import java.util.Map;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.param.ShareParam;
import cn.bestsort.model.param.UploadSuccessCallbackParam;
import cn.bestsort.model.vo.UploadTokenVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    boolean canSuperUpload(String md5, FileNamespace fileNamespace);

    /**
     * 获取文件列表
     * @param dirId 文件夹ID
     * @param user  用户(用作鉴权)
     * @param status 文件状态
     * @return      文件列表
     */
    Page<List<FileMapping>> listFiles(Pageable pageable, Long dirId, Long user, Status status);

    /**
     * 根据分享的url获取文件列表
     * @param url
     * @return 文件列表
     */
    List<FileMapping> listFilesByShare(String url);
    /**
     * 逻辑删除文件
     * @param fileId    文件映射 Id
     * @param user      对应用户
     * @param remove    是否从回收站移除
     */
    @Transactional(rollbackFor = Exception.class)
    void deleteFile(Long fileId, User user, boolean remove);

    /**
     * 创建链接以供下载使用， 链接有效期为 expire(单位: S)
     * @param mappingId 需要下载的文件id
     * @param user   用户
     * @param expire 链接过期时间
     * @return       生成后的链接
     */
    String createDownloadLink(Long mappingId, User user, Long expire);

    /**
     * 创建上传所需要的token和相关参数
     * @param namespace 对应的文件系统实现
     * @param config    额外配置，由对应文件系统自行处理
     * @return          VO
     */
    UploadTokenVO createUploadToken(FileNamespace namespace, Map<String, String> config);
    /**
     * 创建分享链接, 返回结果为链接地址, 类似如下:
     * https://{host}/share/{randomKey}/{path}
     * @param param 参数
     * @param user  创建者
     * @return      如上
     */
    String  createShareLink(ShareParam param, User user);

    /**
     * 上传成功， 数据库插入对应数据
     * @param user      上传用户
     * @param param     相关参数
     */
    @Transactional(rollbackFor = Exception.class)
    void uploadSuccess(User user, UploadSuccessCallbackParam param);
}
