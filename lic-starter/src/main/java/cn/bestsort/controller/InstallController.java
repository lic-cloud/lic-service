package cn.bestsort.controller;

import cn.bestsort.model.enums.InitStep;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.vo.CacheSystemVO;
import cn.bestsort.model.vo.EnumsVO;
import cn.bestsort.model.vo.OtherSetVO;
import cn.bestsort.service.MetaInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public Integer getStep() {
        return InitStep.indexOf(metaInfoService.getMetaOrDefaultStr(LicMetaEnum.INIT_STATUS));
    }

    @ApiOperation("添加缓存与系统设置")
    @PostMapping("/addCacheAndSystem")
    public void cacheSystem(@RequestBody CacheSystemVO cacheSystemVO) {
        metaInfoService.updateMeta(LicMetaEnum.CACHE_TYPE, cacheSystemVO.getCache());
        metaInfoService.updateMeta(LicMetaEnum.File_NAME_SPACE, cacheSystemVO.getSystem());
        metaInfoService.updateMeta(LicMetaEnum.INIT_STATUS, InitStep.STEP_3.getKey());
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
        metaInfoService.updateMeta(LicMetaEnum.INIT_STATUS, InitStep.FINISH.getKey());
    }
    @ApiOperation(value = "获取缓存类型")
    @GetMapping("/getCache")
    public List<EnumsVO> listCache() {
        return metaInfoService.getCache();
    }
    @ApiOperation(value = "获取系统类型")
    @GetMapping("/getSystem")
    public List<EnumsVO> listSystem() {
        return metaInfoService.getSystem();
    }
}
