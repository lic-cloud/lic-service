package cn.bestsort.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.bestsort.exception.LicException;
import cn.bestsort.model.dto.FileDTO;
import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.enums.file.LocalHostMetaEnum;
import cn.bestsort.model.vo.UploadTokenVO;
import cn.bestsort.util.FileUtil;
import cn.bestsort.util.UrlUtil;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:58
 */

@Service
public class EmptyFileManagerImpl extends AbstractFileManager {

    @Override
    public String downloadLink(String path, Long expire) {
        String fullPath = FileUtil.unionPath(
            metaInfoService.getMetaObj(String.class, LocalHostMetaEnum.ROOT_PATH),
            metaInfoService.getMetaObj(String.class, LocalHostMetaEnum.DATA_DIR),
            path);
        String randomKey = UUID.randomUUID().toString();
        cache().put(randomKey, fullPath, expire, TimeUnit.SECONDS);
        String url = String.format("%s/%s/server",
                                   metaInfoService.getMetaObj(String.class, LicMetaEnum.HOST),
                                   DOWNLOAD_LINK_PATH);
        return UrlUtil.appendParam(url, "key", randomKey);
    }

    @Override
    public void move(FileDTO fileDTO, Long targetDirId) throws LicException {

    }

    @Override
    public UploadTokenVO generatorUploadVO(Map<String, String> config) {
        return null;
    }

    @Override
    public void rename(FileDTO fileDTO, String targetName) {

    }

    @Override
    public void del(List<FileInfo> fileDTO) {

    }

    @Override
    public boolean match(FileNamespace namespace) {
        return FileNamespace.LOCALHOST.equals(namespace);
    }
}
