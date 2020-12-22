package cn.bestsort.service.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.enums.Status;
import cn.bestsort.repository.FileMappingRepository;
import cn.bestsort.service.AbstractBaseService;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.UserService;
import cn.bestsort.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-10 20:31
 */
@Slf4j
@Service
public class FileMappingServiceImpl extends AbstractBaseService<FileMapping, Long> implements FileMappingService {

    final FileMappingRepository repo;
    final UserService           userService;

    @Override
    public void changeSize4Parents(Long pid, Float size) {
        FileMapping buffer;
        List<FileMapping> res = new LinkedList<>();
        while (pid != 0 && (buffer = getMapping(pid, Status.VALID)) != null) {
            buffer.setSize(buffer.getSize() + size);
            res.add(buffer);
            pid = buffer.getPid();
        }
        if (CollectionUtils.isEmpty(res)) {
            return;
        }
        log.info("{}", JSON.toJSONString(res));
        saveAll(res);
    }

    @Override
    public Page<FileMapping> listUserFiles(Pageable page, Long pid, Status status, Boolean onlyDir) {
        if (onlyDir) {
            return repo.findAllByPidAndOwnerIdAndStatusAndIsDir(page, pid, UserUtil.getLoginUserId(), status, true);
        }
        if (Status.VALID.equals(status)) {
            return repo.findAllByPidAndOwnerIdAndStatus(page, pid, UserUtil.getLoginUserId(), status);
        } else {
            return repo.findAllByOwnerIdAndStatus(page, UserUtil.getLoginUserId(), status);
        }
    }

    @Override
    public List<FileMapping> listFiles(Long pid) {
        return repo.findAllByPid(pid);
    }

    @Override
    public List<FileMapping> listUserFilesWithoutPage(Long dirId, Status status, Boolean onlyDir) {
        return repo.findAllByPidAndOwnerIdAndStatusAndIsDir(dirId, UserUtil.getLoginUserId(), status, onlyDir);
    }

    @Override
    public FileMapping getMapping(Long mappingId, Status status) {
        FileMapping mapping = getById(mappingId);
        if (status.equals(mapping.getStatus())) {
            UserUtil.checkIsOwner(mapping.getOwnerId());
            return mapping;
        }
        throw ExceptionConstant.NOT_FOUND_ITEM;
    }

    @Override
    public String fullPath(Long dirId) {

        if (dirId == 0) {
            return File.separator;
        }
        FileMapping fileMapping;
        StringBuilder  res = new StringBuilder();
        // 不断向上回溯父路径
        do {
            fileMapping = getById(dirId);
            res.append(fileMapping.getFileName()).append(File.separator);
            dirId = fileMapping.getPid();
        } while (dirId != 0);
        return res.reverse().toString();
    }

    @Override
    public void moveMapping(Boolean isCopy, Long mappingId, Long targetDirPid) {
        FileMapping mapping = getById(mappingId);
        mustIsDir(targetDirPid, true);
        if (isCopy) {
            mapping.setId(null);
        }
        mapping.setPid(targetDirPid);
        save(mapping);
    }

    @Override
    public Boolean checkCapPass() {
        return checkCapPass(0.0f);
    }

    @Override
    public void rename(String name, Long id) {
        FileMapping fileMapping = getById(id);
        UserUtil.checkIsOwner(fileMapping.getOwnerId());
        // 当前目录下有同名文件
        if (repo.findAllByPid(fileMapping.getPid())
            .stream().map(FileMapping::getFileName)
            .collect(Collectors.toList())
            .contains(name)) {
            throw ExceptionConstant.TARGET_EXIST;
        }
        fileMapping.setFileName(name);
        save(fileMapping);
    }

    @Override
    public Boolean checkCapPass(float add) {
        User user = userService.getById(UserUtil.getLoginUserId());

        return user.getInfiniteCapacity() ||
            user.getUsedCapacity() + add <= user.getTotalCapacity();
    }

    private void mustIsDir(Long pid, boolean mustBeOwner) {
        if (pid == 0) {
            return;
        }
        FileMapping mapping = getById(pid);
        if (!mapping.getIsDir()) {
            throw ExceptionConstant.NOT_FOUND_SUCH_FILE;
        }
        if (mustBeOwner) {
            UserUtil.checkIsOwner(mapping.getOwnerId());
        }
    }
    protected FileMappingServiceImpl(
        FileMappingRepository repository, UserService userRepository) {
        super(repository);
        repo = repository;
        this.userService = userRepository;
    }

}
