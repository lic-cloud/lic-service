package cn.bestsort.listener;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.cache.CacheStoreType;
import cn.bestsort.constant.MetaEnum;
import cn.bestsort.service.impl.MetaInfoService;
import lombok.extern.slf4j.Slf4j;
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
    ApplicationContext    context;
    @Autowired
    MetaInfoService metaInfoService;
    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        init();
        String version = metaInfoService.getMeta(MetaEnum.VERSION, "V1.0");
        log.info("Online Judge V{} start success, click url to view [ swagger ] document {}",
            version, AnsiOutput.toString(AnsiColor.BLUE,
                metaInfoService.getMetaOrDefault(MetaEnum.HOST) + "/swagger-ui.html"));

        log.info("Online Judge V{} start success, click url to view [ knife4j ] document {}",
            version, AnsiOutput.toString(AnsiColor.BLUE,
                metaInfoService.getMetaOrDefault(MetaEnum.HOST) + "/doc.html"));
    }

    private void init() {
        metaInfoService.refresh();
        // init cache
        handler.init(context, CacheStoreType
            .valueOf(metaInfoService.getMetaOrDefault(MetaEnum.CACHE_TYPE)));
    }
}

