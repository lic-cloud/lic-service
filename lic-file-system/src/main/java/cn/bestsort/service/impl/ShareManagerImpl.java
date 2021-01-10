package cn.bestsort.service.impl;

import java.sql.Timestamp;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.FileShare;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.param.ShareParam;
import cn.bestsort.service.FileInfoService;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.FileShareService;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.service.ShareManager;
import cn.bestsort.util.TimeUtil;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    final MetaInfoService    metaInfoService;
    public Page<FileMapping> listFilesByShare(String url, Long pid, Pageable pageable) {
        FileMapping fileMapping = fetchMappingByUrl(url);
        // 分享的是文件
        if (!fileMapping.getIsDir()) {
            return new PageImpl<>(ImmutableList.of(fileMapping), pageable, 1L);
        }
        // pid 为 -1 表示用户刚进入查看分享的界面，此时不会传pid进来
        if (pid == -1) {
            pid = fileMapping.getId();
        }
        return mappingService.listUserFiles(pageable,fileMapping.getOwnerId(), pid, Status.VALID, false);
    }

    @Override
    public FileMapping getMapping(Long id, String url) {
        FileMapping res = mappingService.getMapping(id, Status.VALID, true);
        Page<FileMapping> page = listFilesByShare(url, res.getPid(), Pageable.unpaged());
        for (FileMapping mapping : page.getContent()) {
            if (mapping.getId().equals(id)) {
                return mapping;
            }
        }
        throw ExceptionConstant.NOT_FOUND_ITEM;
    }

    @Override
    public Long count(String url, Long pid) {
        return fileShareImpl.count();
    }


    private FileMapping fetchMappingByUrl(String url) {
        FileShare fileShare = fileShareImpl.getByUrl(url)
            .orElseThrow(() -> ExceptionConstant.NOT_FOUND_ITEM);
        Timestamp expire = fileShare.getExpire();
        if (!expire.equals(metaInfoService.getMetaObj(Timestamp.class, LicMetaEnum.TIME_ZERO)) &&
            fileShare.getExpire().before(TimeUtil.now())) {
            throw ExceptionConstant.EXPIRED;
        }
        return mappingService.getById(fileShare.getMappingId());
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
                            FileMappingService mappingService, FileShareService fileShareImpl,
                            MetaInfoService metaInfoService) {
        this.manager = manager;
        this.fileInfoImp = fileInfoImp;
        this.mappingService = mappingService;
        this.fileShareImpl = fileShareImpl;
        this.metaInfoService = metaInfoService;
    }
}
