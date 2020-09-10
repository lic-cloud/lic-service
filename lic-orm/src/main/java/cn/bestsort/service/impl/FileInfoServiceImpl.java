package cn.bestsort.service.impl;

import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.repository.FileInfoRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.FileInfoService;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:21
 */
public class FileInfoServiceImpl extends AbstractBaseService<FileInfo, Long> implements FileInfoService {

    protected FileInfoServiceImpl(
        FileInfoRepository repository) {
        super(repository);
    }
}
