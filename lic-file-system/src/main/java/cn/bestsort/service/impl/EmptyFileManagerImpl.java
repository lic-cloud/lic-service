package cn.bestsort.service.impl;

import java.io.File;
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
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:58
 */

@Service
public class EmptyFileManagerImpl extends AbstractFileManager {

    @Override
    public String downloadLink(FileDTO fileDTO, Long expire) {
        String dataDir = metaInfoService.getMetaOrDefault(LocalHostMetaEnum.DATA_DIR);
        String fullPath = metaInfoService.getMeta(LocalHostMetaEnum.ROOT_PATH) + dataDir + File.separator + fileDTO.getFileInfo().getPath();
        String randomKey = UUID.randomUUID().toString();
        String mappingPath = String.format("%s/%s/%s%s", metaInfoService.getMetaOrDefault(LicMetaEnum.HOST),
                              DOWNLOAD_LINK_PATH,
                              FileNamespace.LOCALHOST,
                              randomKey);
        cache().put(randomKey, fullPath, expire, TimeUnit.SECONDS);
        return mappingPath;
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
