package cn.bestsort.repository.impl;

import cn.bestsort.repository.MetaInfoRepository;
import org.springframework.stereotype.Component;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:19
 */
@Component
public class MetaInfoRepositoryImpl {
    final MetaInfoRepository metaInfoRepo;


    public MetaInfoRepositoryImpl(MetaInfoRepository metaInfoRepo) {
        this.metaInfoRepo = metaInfoRepo;
    }
}
