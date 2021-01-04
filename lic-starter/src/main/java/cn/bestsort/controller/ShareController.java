package cn.bestsort.controller;

import java.sql.Timestamp;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.param.ShareParam;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.service.ShareManager;
import cn.bestsort.util.PageUtil;
import cn.bestsort.util.UserUtil;
import cn.bestsort.util.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-07 10:39
 */

@Api(tags = "分享")
@RestController
@RequestMapping("/share")
public class ShareController {
    @Autowired
    LicFileManager fileManager;

    @Autowired
    ShareManager shareManager;
    @Autowired
    MetaInfoService metaInfoService;


    @ApiOperation("获取文件列表(返回PageTableResponse, 用于表格渲染)")
    @GetMapping("/{url}")
    public PageTableResponse list(@RequestParam(defaultValue = "-1") Long pid,
                                  @PathVariable String url,
                                  @ApiIgnore @PageableDefault(size = 20) Pageable pageable) {
        return PageUtil.toPageTable(
            shareManager.listFilesByShare(url, pid, pageable),
            shareManager.count(url, pid));
    }

    @GetMapping("/mapping/{url}")
    public ResponseEntity<FileMapping> getShare(@RequestParam Long id,
                                                @PathVariable String url) {
        return ResponseEntity.ok(shareManager.getMapping(id, url));
    }

    @ApiOperation("新建/更新文件分享")
    @PostMapping("/create")
    public ResponseEntity<String> createShareLink(@RequestParam Long id) {
        ShareParam param = new ShareParam();
        param.setExpire(metaInfoService.getMetaObj(Timestamp.class, LicMetaEnum.TIME_ZERO));
        param.setMappingId(id);
        String res = shareManager.createShareLink(param, UserUtil.mustGetLoginUser());
        return ResponseEntity.ok(metaInfoService.getMetaOrDefaultStr(LicMetaEnum.HOST) + "/share/" + res);
    }


    @ApiOperation("取消文件分享")
    @DeleteMapping
    public ResponseEntity<Boolean> cancelShareLink(@RequestParam Long mappingId) {
        return ResponseEntity.ok(shareManager.cancelShareLink(mappingId, UserUtil.mustGetLoginUser()));
    }
}
