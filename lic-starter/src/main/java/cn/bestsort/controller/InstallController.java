package cn.bestsort.controller;

import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.model.param.install.CacheSettingParam;
import cn.bestsort.service.MetaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 10:21
 */

@RestController
public class InstallController {
    @Autowired
    MetaInfoService metaInfoService;
    ResponseEntity<Boolean> install(CacheSettingParam param) {
        if (metaInfoService.getMetaObj(Boolean.class, LicMetaEnum.INSTALLED)) {
            throw ExceptionConstant.LIC_INSTALLED;
        }
        // TODO install
        metaInfoService.updateMeta(LicMetaEnum.INSTALLED, Boolean.TRUE);
        return ResponseEntity.ok(true);
    }
}
