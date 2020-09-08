package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.dto.FileDTO;
import cn.bestsort.entity.FileInfo;
import cn.bestsort.enums.FileNamespace;
import cn.bestsort.exception.LicException;
import cn.bestsort.service.FileManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:58
 */

@Service
public class EmptyFileManagerImpl implements FileManager {

    @Override
    public String realDir(FileDTO fileDTO) {
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
        return false;
    }
}
