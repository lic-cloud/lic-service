package cn.bestsort.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;

import cn.bestsort.model.entity.MetaInfo;
import cn.bestsort.model.enums.KeyValEnum;
import cn.bestsort.repository.MetaInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-09 08:38
 */
@Service
public class MetaInfoService extends AbstractBaseService<MetaInfo, Long> {

    final MetaInfoRepository metaInfoRepo;
    private final ConcurrentHashMap<String, String> metaMap = new ConcurrentHashMap<>();

    public String getMeta(String metaKey) {
        return metaMap.get(metaKey);
    }

    public String getMeta(String metaKey, String defaultVal) {
        String value = metaMap.get(metaKey);
        if (value == null) {
            metaInfoRepo.saveAndFlush(new MetaInfo(metaKey, defaultVal));
            metaMap.put(metaKey, defaultVal);
            value = defaultVal;
        }
        return value;
    }

    public <T> T getMetaObj(Class<T> clazz, KeyValEnum licMetaEnum) {
        String res;
        if ((res = getMeta(licMetaEnum)) != null) {
            if (String.class.equals(clazz)) {
                return (T)res;
            }
            return JSON.parseObject(res, clazz);
        }
        return KeyValEnum.get(clazz, licMetaEnum);
    }

    public String getMeta(KeyValEnum metaKey) {
        return getMeta(metaKey.getKey().toString());
    }

    public String getMeta(KeyValEnum metaKey, String defaultVal) {
        return getMeta(metaKey.getKey().toString(), defaultVal);
    }

    public String getMetaOrDefaultStr(KeyValEnum metaKey) {
        return getMeta(metaKey.getKey().toString(), KeyValEnum.get(String.class, metaKey));
    }

    /**
     * 刷新内存中的 meta 信息
     */
    public void refresh() {
        List<MetaInfo> metaInfos = listAll();
        metaMap.clear();
        for (MetaInfo metaInfo : metaInfos) {
            metaMap.put(metaInfo.getMetaKey(), metaInfo.getMetaVal());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateMeta(KeyValEnum licMetaEnum, Object metaVal) {
        String metaKey = licMetaEnum.getKey().toString();
        Optional<MetaInfo> metaInfoOpt = metaInfoRepo.findByMetaKey(metaKey);
        MetaInfo metaInfo;
        if (metaInfoOpt.isEmpty()) {
            metaInfo = new MetaInfo();
            metaInfo.setMetaKey(metaKey);
            metaInfo.setMetaVal(metaVal.toString());
        } else {
            metaInfo = metaInfoOpt.get();
        }
        metaInfoRepo.saveAndFlush(metaInfo);
        metaMap.put(metaKey, metaVal.toString());
    }

    protected MetaInfoService(
        MetaInfoRepository repository) {
        super(repository);
        this.metaInfoRepo = repository;
    }
}
