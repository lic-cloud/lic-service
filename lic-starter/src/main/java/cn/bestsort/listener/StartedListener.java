package cn.bestsort.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.cache.CacheStoreType;
import cn.bestsort.config.StaterProperties;
import cn.bestsort.model.enums.FileNamespace;
import cn.bestsort.model.enums.KeyEnum;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.service.BaseService;
import cn.bestsort.service.MetaInfoService;
import cn.bestsort.util.DebugUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

/**
 * after application start need to do
 *
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 08:49
 */
@Slf4j
@Configuration
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    CacheHandler handler;
    @Autowired
    ApplicationContext context;
    @Autowired
    MetaInfoService metaInfoService;
    @Autowired
    StaterProperties properties;


    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        init();
        String version = metaInfoService.getMeta(LicMetaEnum.VERSION, "V1.0");
        log.info("Lic[{}] start success, click url to view [ swagger ] document {}",
            version, AnsiOutput.toString(AnsiColor.GREEN,
                metaInfoService.getMetaOrDefaultStr(LicMetaEnum.HOST) + "/swagger-ui.html"));

        log.info("Lic[{}] start success, click url to view [ knife4j ] document {}",
            version, AnsiOutput.toString(AnsiColor.GREEN,
                metaInfoService.getMetaOrDefaultStr(LicMetaEnum.HOST) + "/doc.html"));
    }

    private void init() {
        metaInfoService.refresh();
        // init cache
        CacheStoreType curCache = KeyEnum.keyToEnum(
            CacheStoreType.class, metaInfoService.getMetaOrDefaultStr(LicMetaEnum.CACHE_TYPE));
        handler.init(context, curCache);
        log.info(
            "System Init Completed, Cache is [{}], FileSystem is [{}]",
            AnsiOutput.toString(AnsiColor.GREEN, curCache.getKey()),
            AnsiOutput.toString(
                AnsiColor.GREEN,
                KeyEnum.keyToEnum(FileNamespace.class,
                    metaInfoService.getMetaOrDefaultStr(LicMetaEnum.File_NAME_SPACE)).getKey())
        );
        // 根据DEBUG_DATA生成假数据
        if (properties.getGeneratorFakeData()) {
            // get all BaseService impl and save to map.
            HashMap<Class<?>, BaseService<?, ?>> map = new HashMap<>(16);
            context.getBeansOfType(BaseService.class).values().forEach(o -> map.put(o.getTemplateType(), o));
            try {
                generatorDebugData(map);
            } catch (IOException ignore) {
            }
        }
    }

    private void generatorDebugData(HashMap<Class<?>, BaseService<?, ?>> map) throws IOException {
        // 从文件中加载调试数据
        String json = DebugUtil.loadDebugData();

        // parse JSON string and save to database
        JSONArray array = JSON.parseObject(json).getJSONArray(DebugUtil.ALL_DATA);
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            List lst;
            Class<?> clazz;
            // try parse
            try {
                clazz = Class.forName(object.getString(DebugUtil.ALL_CLASS_NAME));
                lst = JSON.parseArray(object.getString(DebugUtil.DATA), clazz);
            } catch (Exception ignore) {
                log.error("假数据构造失败, data-> {}", object.toJSONString());
                continue;
            }
            // if entity is UserEntity, encode user's password
            // if entity is other entity, fetch default fields
            DebugUtil.filedAudit(lst);

            // save to database
            if (CollectionUtils.isNotEmpty(lst)) {
                map.get(lst.get(0).getClass()).saveAll(lst);
            }
            log.info("debug data generator success, class -> [{}], data -> {}",
                clazz.getName(), JSON.toJSONString(lst));
        }
    }
}

