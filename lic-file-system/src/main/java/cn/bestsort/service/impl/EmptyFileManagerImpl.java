package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.dto.FileDTO;
import cn.bestsort.exception.LicException;
import cn.bestsort.model.FileInfoDO;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.service.FileManager;
import lombok.NonNull;
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
    public List<FileInfoDO> list(@NonNull FileDTO fileDTO) {
        return null;
    }

    @Override
    public String realDir(FileDTO fileDTO) {
        return null;
    }

    @Override
    public void move(String oldPath, String curPath) throws LicException {

    }

    @Override
    public void upload(String path, MultipartFile[] files) {

    }

    @Override
    public void rename(String path, String oldName, String curName) {

    }

    @Override
    public boolean match(FileNamespace namespace) {
        return false;
    }
}
