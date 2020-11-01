package cn.bestsort.service.impl;

import java.util.List;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.FileShare;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.param.ShareParam;
import cn.bestsort.service.FileInfoService;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.FileShareService;
import cn.bestsort.service.ShareManager;
import cn.bestsort.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-27 08:04
 */
@Slf4j
@Service
public class ShareManagerImpl implements ShareManager {

    final FileManagerHandler manager;
    final FileInfoService    fileInfoImp;
    final FileMappingService mappingService;
    final FileShareService   fileShareImpl;

    @Override
    public List<FileMapping> listFilesByShare(String url, Long pid) {
        FileShare fileShare = fileShareImpl.getByUrl(url)
            .orElseThrow(() -> ExceptionConstant.NOT_FOUND_ITEM);
        if (fileShare.getExpire().before(TimeUtil.now())) {
            throw ExceptionConstant.EXPIRED;
        }
        FileMapping fileMapping = mappingService.getById(fileShare.getMappingId());
        // pid 为null表示用户刚进入查看分享的界面，此时不会传pid进来
        if (pid == null) {
            return List.of(fileMapping);
        }
        // 查看分享的文件夹的子目录
        // 向上查询检查当前子目录是否是当前分享的文件夹内的文件
        // 根目录不可能被分享
        while (!pid.equals(fileMapping.getId()) && fileMapping.getPid() != 0L) {
            fileMapping = mappingService.getById(fileMapping.getPid());
        }
        if (pid.equals(fileMapping.getId())) {
            return mappingService.listFiles(pid);
        }
        throw ExceptionConstant.PARAM_ILLEGAL;
    }

    @Override
    public Boolean cancelShareLink(Long mappingId, User owner) {
        FileShare fileShare = fileShareImpl.getByMappingId(mappingId).orElseThrow(() -> ExceptionConstant.NOT_FOUND_ITEM);
        if (!fileShare.getOwnerId().equals(owner.getId())) {
            throw ExceptionConstant.UNAUTHORIZED;
        }
        fileShareImpl.remove(fileShare);
        return true;
    }

    @Override
    public String createShareLink(ShareParam param, User user) {
        mappingService.mustExistById(param.getMappingId());
        FileShare fileShare;
        // url不为空时，表示更新
        if (StringUtils.isNotBlank(param.getUrl())) {
            fileShare = fileShareImpl.getByUrl(param.getUrl())
                .orElseThrow(() -> ExceptionConstant.UNAUTHORIZED);
            fileShare.setPassword(param.getPassword());
            fileShare.setExpire(param.getExpire());
        }
        else {
            String url;
            // 防止生成的url产生碰撞
            do {
                url = RandomStringUtils.randomAlphanumeric(16);
            } while (fileShareImpl.existsByUrl(url));
            fileShare = new FileShare(param.getMappingId(), user.getUsername(),
                          user.getId(), param.getPassword(), url, param.getExpire());
        }
        return fileShareImpl.save(fileShare).getUrl();
    }


    public ShareManagerImpl(FileManagerHandler manager, FileInfoService fileInfoImp,
                            FileMappingService mappingService, FileShareService fileShareImpl) {
        this.manager = manager;
        this.fileInfoImp = fileInfoImp;
        this.mappingService = mappingService;
        this.fileShareImpl = fileShareImpl;
    }
}
