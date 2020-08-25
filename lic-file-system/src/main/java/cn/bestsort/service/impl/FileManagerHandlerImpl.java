package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.service.FileManager;
import cn.bestsort.service.FileManagerHandler;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:37
 */

@Service
public class FileManagerHandlerImpl implements FileManagerHandler {

    final List<FileManager> fileManagers;


    @Override
    public FileManager handle(FileNamespace namespace) {
        for (FileManager fileManager : fileManagers) {
            if (fileManager.match(namespace)) {
                return fileManager;
            }
        }
        throw ExceptionConstant.NOT_FOUND_FILE_SYS;
    }

    public FileManagerHandlerImpl(List<FileManager> fileManagers) {this.fileManagers = fileManagers;}

}
