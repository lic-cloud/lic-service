package cn.bestsort.service.impl;

import java.io.File;
import java.util.List;

import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.exception.LicException;
import cn.bestsort.model.dto.FileDTO;
import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.file.LocalHostMetaEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:58
 */

@Service
public class EmptyFileManagerImpl extends AbstractFileManager {

    public static final String ROOT_PATH = System.getProperty("user.dir") +
        File.separator;


    @Override
    public String downloadLink(FileDTO fileDTO, Long expire) {

        String root = metaInfoService.getMetaOrDefault(LocalHostMetaEnum.DATA_DIR);

        return null;
    }

    @Override
    public void move(FileDTO fileDTO, Long targetDirId) throws LicException {

    }

    @Override
    public void upload(FileDTO fileDTO, MultipartFile[] files) {

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
