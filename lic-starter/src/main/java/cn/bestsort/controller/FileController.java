package cn.bestsort.controller;

import java.util.List;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.Status;
import cn.bestsort.model.vo.FileDetailVO;
import cn.bestsort.service.FileManagerHandler;
import cn.bestsort.service.FileMappingService;
import cn.bestsort.service.FileShareService;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.util.PageUtil;
import cn.bestsort.util.UserUtil;
import cn.bestsort.util.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    FileShareService fileShareService;
    @Autowired
    FileManagerHandler handler;
    @Autowired
    CacheHandler cache;
    private static final FileMapping ROOT_PATH_MAPPING = new FileMapping(
        "根目录", null, null, null, null, true, false, Status.VALID);

    @ApiOperation("获取文件详情")
    @GetMapping("/detail")
    public ResponseEntity<FileDetailVO> getFileDetail(@RequestParam Long mappingId) {
        FileDetailVO vo = new FileDetailVO();
        vo.setMapping(mappingService.getMapping(mappingId, Status.VALID));
        //TODO share
        return ResponseEntity.ok(vo);
    }

    @ApiOperation("文件重命名")
    @PostMapping("/name")
    public ResponseEntity<Boolean> rename(@RequestParam Long id,
                                          @RequestParam String name) {
        mappingService.rename(name, id);
        return ResponseEntity.ok(true);
    }

    @ApiOperation("获取文件列表(返回PageTableResponse, 用于表格渲染)")
    @GetMapping("/page")
    public PageTableResponse list(@RequestParam(defaultValue = "VALID") Status status,
                                  @RequestParam(defaultValue = "0") Long pid,
                                  @RequestParam(defaultValue = "false") Boolean onlyDir,
                                  @ApiIgnore @PageableDefault(size = 20) Pageable pageable) {
        return PageUtil.toPageTable(
            mappingService.listUserFiles(pageable, pid, status, onlyDir),
            mappingService.count());
    }

    @ApiOperation("获取文件列表(返回List)")
    @GetMapping("/list")
    public ResponseEntity<List<FileMapping>> list(@RequestParam(defaultValue = "VALID") Status status,
                                  @RequestParam(defaultValue = "0") Long pid,
                                  @RequestParam(defaultValue = "false") Boolean onlyDir) {
        return ResponseEntity.ok(
            mappingService.listUserFilesWithoutPage(pid, status, onlyDir)
        );
    }

    @ApiOperation("获取指定Mapping的信息")
    @GetMapping
    public ResponseEntity<FileMapping> get(@RequestParam Long mappingId,
                                           @RequestParam(defaultValue = "VALID") Status status) {
        if (mappingId == 0) {
            return ResponseEntity.ok(ROOT_PATH_MAPPING);
        }
        return ResponseEntity.ok(mappingService.getMapping(mappingId, status));
    }

    @ApiOperation("创建mapping, 用于文件夹创建")
    @PostMapping("/dir")
    public ResponseEntity<FileMapping> create(Long pid, String name) {
        FileMapping mapping = new FileMapping(
            name, null, UserUtil.getLoginUserId(),
            Float.parseFloat("0"), pid, true, false, Status.VALID
        );
        fileManager.createMapping(mapping);
        return ResponseEntity.ok(mapping);
    }
    @ApiOperation("文件/文件夹的移动、复制")
    @PostMapping
    public ResponseEntity<Boolean> move(@RequestParam(defaultValue = "false") Boolean isCopy,
                                        @RequestParam Long mappingId,
                                        @RequestParam Long targetDirPid) {
        mappingService.moveMapping(isCopy, mappingId, targetDirPid);
        return ResponseEntity.ok(true);
    }


    @ApiOperation("将文件移动到回收站")
    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam Long id,
                                          @RequestParam Boolean isLogicRemove) {
        fileManager.deleteFile(id, isLogicRemove);
        return ResponseEntity.ok(true);
    }
}
