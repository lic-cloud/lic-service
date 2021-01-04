package cn.bestsort.service;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.param.ShareParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-27 08:04
 */
public interface ShareManager {
    /**
     * 创建分享链接, 返回结果为链接地址, 类似如下:
     * https://{host}/share/{randomKey}/{path}
     * @param param 参数
     * @param user  创建者
     * @return      如上
     */
    String  createShareLink(ShareParam param, User user);


    /**
     * 根据分享的url获取文件列表,当分享的是文件夹时，pid被用于查找其子文件夹
     * @param url key
     * @param pid 当为NULL时返回被分享文件夹/文件
     * @return 文件列表
     */
    Page<FileMapping> listFilesByShare(String url, Long pid, Pageable pageable);

    FileMapping getMapping(Long id, String url);

    Long count(String url, Long pid);

    /**
     * 取消文件分享
     * @param mappingId 文件映射
     * @param owner 映射所有者
     * @return 是否成功
     */
    Boolean cancelShareLink(Long mappingId, User owner);
}
