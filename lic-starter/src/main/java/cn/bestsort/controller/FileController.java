package cn.bestsort.controller;

import java.util.List;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.util.PageUtil;
import cn.bestsort.util.UserUtil;
import cn.bestsort.util.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-07 10:38
 */

@Api(tags = "文件映射")
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    LicFileManager fileManager;
    @Autowired
    FileMappingService mappingService;
    @Autowired
    FileManagerHandler handler;
    @Autowired
    CacheHandler cache;

    @ApiOperation("获取文件列表(返回PageTableResponse, 用于表格渲染)")
    @GetMapping("/page")
    public PageTableResponse list(@RequestParam(defaultValue = "VALID") Status status,
                                  @RequestParam(defaultValue = "0") Long pid,
                                  @RequestParam(defaultValue = "false") Boolean onlyDir,
                                  @ApiIgnore @PageableDefault(size = 20) Pageable pageable) {
        return PageUtil.toPageTable(
            mappingService.listUserFiles(pageable, pid, UserUtil.mustGetLoginUser().getId(), status, onlyDir),
            mappingService.count());
    }

    @ApiOperation("获取文件列表(返回List)")
    @GetMapping("/list")
    public List<FileMapping> list(@RequestParam(defaultValue = "VALID") Status status,
                                  @RequestParam(defaultValue = "0") Long pid,
                                  @RequestParam(defaultValue = "false") Boolean onlyDir) {
        return mappingService.listUserFilesWithoutPage(pid, UserUtil.mustGetLoginUser().getId(), status, onlyDir);
    }

}
