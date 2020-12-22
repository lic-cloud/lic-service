package cn.bestsort.controller;

import java.util.List;

import cn.bestsort.model.entity.FileMapping;
import cn.bestsort.model.param.ShareParam;
import cn.bestsort.service.LicFileManager;
import cn.bestsort.service.ShareManager;
import cn.bestsort.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-10-07 10:39
 */

@Api(tags = "分享")
@Controller
@RequestMapping("/share")
public class ShareController {
    @Autowired
    LicFileManager fileManager;

    @Autowired
    ShareManager shareManager;

    @ApiOperation("获取分享的文件列表(pid为空则返回当前分享文件夹的根目录)")
    @GetMapping("/{url}")
    public ResponseEntity<List<FileMapping>> listShare(@PathVariable String url,
                                       @RequestParam(required = false) Long pid) {
        return ResponseEntity.ok(shareManager.listFilesByShare(url, pid));
    }


    @ApiOperation("新建/更新文件分享")
    @PostMapping
    public ResponseEntity<String> createShareLink(ShareParam shareParam) {
        return ResponseEntity.ok(shareManager.createShareLink(shareParam, UserUtil.mustGetLoginUser()));
    }


    @ApiOperation("取消文件分享")
    @DeleteMapping
    public ResponseEntity<Boolean> cancelShareLink(@RequestParam Long mappingId) {
        return ResponseEntity.ok(shareManager.cancelShareLink(mappingId, UserUtil.mustGetLoginUser()));
    }
}
