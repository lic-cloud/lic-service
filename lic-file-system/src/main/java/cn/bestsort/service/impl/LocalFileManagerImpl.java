package cn.bestsort.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.FileInfo;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.enums.file.LocalHostMetaEnum;
import cn.bestsort.model.vo.UploadTokenVO;
import cn.bestsort.util.FileUtil;
import cn.bestsort.util.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-08-24 17:58
 */
@Slf4j
@Service
public class LocalFileManagerImpl extends AbstractFileManager {

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
    public UploadTokenVO generatorUploadVO(Map<String, String> config) {
        return null;
    }

    @Override
    public void delEntity(List<FileInfo> fileDTO) {
        for (FileInfo fileInfo : fileDTO) {

            String fullPath = FileUtil.unionPath(
                metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.ROOT_PATH),
                metaInfoService.getMetaOrDefaultStr(LocalHostMetaEnum.DATA_DIR),
                fileInfo.getPath(),
                fileInfo.getFileName()
            );
            File file = new File(fullPath);
            int cnt = 3;
            while (!file.delete()) {
                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(3));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cnt--;
                if (cnt == 0) {
                    log.error("{} entity delete failed, full path: {}", fileInfo.getFileName(), fullPath);
                    throw ExceptionConstant.DELETE_FAILED;
                }
                log.info("file: {} delete failed, retry after 3S", fileInfo.getFileName());
            }
        }
    }

    @Override
    public boolean match(FileNamespace namespace) {
        return FileNamespace.LOCALHOST.equals(namespace);
    }
}
