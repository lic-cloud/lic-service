package cn.bestsort.controller;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.param.install.CacheSettingParam;
import cn.bestsort.model.vo.CacheSystemVO;
import cn.bestsort.model.vo.OtherSetVO;
import cn.bestsort.service.MetaInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 10:21
 */

@Api(tags = "系统初始化")
@RestController
@RequestMapping("/install")
public class InstallController {
    @Autowired
    MetaInfoService metaInfoService;

    @ApiOperation("获取初始化到第几步骤")
    @GetMapping("/step")
    public String getStep() {
        return metaInfoService.getMetaOrDefaultStr(LicMetaEnum.INIT_STATUS);
    }

    @ApiOperation("添加缓存与系统设置")
    @PostMapping("/addCacheAndSystem")
    public void cacheSystem(@RequestBody CacheSystemVO cacheSystemVO) {
        metaInfoService.updateMeta(LicMetaEnum.CACHE_TYPE, cacheSystemVO.getCache());
        metaInfoService.updateMeta(LicMetaEnum.File_NAME_SPACE, cacheSystemVO.getSystem());
        metaInfoService.updateMeta(LicMetaEnum.INIT_STATUS, "step3");
    }

    @ApiOperation("其他高级设置")
    @PostMapping("/addOtherSet")
    public void otherSet(@RequestBody OtherSetVO otherSetVO) {
        if (!otherSetVO.getDir().isEmpty()) {
            metaInfoService.updateMeta(LicMetaEnum.RESOURCE_DIR, otherSetVO.getDir());
        } else {
            metaInfoService.updateMeta(LicMetaEnum.RESOURCE_DIR, LicMetaEnum.RESOURCE_DIR.getDefault());
        }
        metaInfoService.updateMeta(LicMetaEnum.RESOURCE_DIR_TITLE_LENGTH, otherSetVO.getLength());
        metaInfoService.updateMeta(LicMetaEnum.CACHE_EXPIRE, otherSetVO.getExpire());
        metaInfoService.updateMeta(LicMetaEnum.CACHE_NULL_EXPIRE, otherSetVO.getTime());
        metaInfoService.updateMeta(LicMetaEnum.CACHE_UNIT, otherSetVO.getUnit());
        metaInfoService.updateMeta(LicMetaEnum.INIT_STATUS, "finish");
    }

    ResponseEntity<Boolean> install(CacheSettingParam param) {
        if (metaInfoService.getMetaObj(Boolean.class, LicMetaEnum.INIT_STATUS)) {
            throw ExceptionConstant.LIC_INSTALLED;
        }
        // TODO install
        metaInfoService.updateMeta(LicMetaEnum.INIT_STATUS, Boolean.TRUE);
        return ResponseEntity.ok(true);
    }
}
