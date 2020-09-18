package cn.bestsort.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import cn.bestsort.cache.CacheHandler;
import cn.bestsort.cache.store.CacheStore;
import cn.bestsort.constant.CachePrefix;
import cn.bestsort.constant.ExceptionConstant;
import cn.bestsort.model.entity.BaseEntity;
import cn.bestsort.model.entity.User;
import cn.bestsort.model.enums.LicMetaEnum;
import cn.bestsort.repository.BaseRepository;
import cn.bestsort.util.CacheUtil;
import cn.bestsort.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:24
 */

@Slf4j
public abstract class AbstractBaseService<DOMAIN extends BaseEntity, ID> implements BaseService<DOMAIN, ID> {
    private final String domainName;
    private final BaseRepository<DOMAIN, ID> repository;

    @Autowired
    protected CacheHandler handler;

    protected AbstractBaseService(BaseRepository<DOMAIN, ID> repository) {
        this.repository = repository;
        @SuppressWarnings("unchecked")
        Class<DOMAIN> domainClass = (Class<DOMAIN>) fetchType();
        domainName = domainClass.getSimpleName();
    }

    private User fetchUserFromSession(boolean nullable, HttpSession session) {
        User userEntity = (User) session.getAttribute(LicMetaEnum.USER_SESSION.getVal());
        if (!nullable && userEntity == null) {
            throw ExceptionConstant.UNAUTHORIZED;
        }
        return userEntity;
    }

    protected User assertLogin(HttpSession session) {
        return fetchUserFromSession(false, session);
    }
    protected User fetchUserInfo(HttpSession session) {
        return fetchUserFromSession(true, session);
    }

    /**
     * List All
     *
     * @return List
     */
    @Override
    public List<DOMAIN> listAll() {
        return repository.findAll();
    }

    /**
     * List all by sort
     *
     * @param sort sort
     * @return List
     */
    @NonNull
    @Override
    public List<DOMAIN> listAll(@NonNull Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        return repository.findAll(sort);
    }

    /**
     * List all by pageable
     *
     * @param pageable pageable
     * @return Page
     */
    @NonNull
    @Override
    public Page<DOMAIN> listAll(@NonNull Pageable pageable) {
        Assert.notNull(pageable, "Pageable info must not be null");
        return repository.findAll(pageable);
    }

    /**
     * List all by ids
     *
     * @param ids ids
     * @return List
     */
    @NonNull
    @Override
    public List<DOMAIN> listAllByIds(Collection<ID> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : repository.findAllById(ids);
    }

    /**
     * List all by ids and sort
     *
     * @param ids  ids
     * @param sort sort
     * @return List
     */
    @NonNull
    @Override
    public List<DOMAIN> listAllByIds(Collection<ID> ids, @NonNull Sort sort) {
        Assert.notNull(sort, "Sort info must not be null");

        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : repository.findAllByIdIn(ids, sort);
    }


    /**
     * Get by id
     *
     * @param id id
     * @return DOMAIN
     */
    @NonNull
    @Override
    public DOMAIN getById(@NonNull ID id) {
        return fetchById(id).orElseThrow(() -> ExceptionConstant.NOT_FOUND_ITEM);
    }

    @NonNull
    @Override
    public Page<DOMAIN> getByIds(@NonNull Collection<ID> ids, Pageable page) {
        return repository.findAllByIdIn(ids, page);
    }

    /**
     * Gets domain of nullable by id.
     *
     * @param id id
     * @return DOMAIN
     */
    @Override
    public DOMAIN getByIdOfNullable(@NonNull ID id) {
        return fetchById(id).orElse(null);
    }

    /**
     * Exists by id.
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean existsById(@NonNull ID id) {
        Assert.notNull(id, domainName + " id must not be null");
        return cache().get(key(fetchClass(), id)) != null || repository.existsById(id);
    }

    /**
     * Must exist by id, or throw NotFoundException.
     *
     * @param id id
     */
    @Override
    public void mustExistById(@NonNull ID id) {
        if (!existsById(id)) {
            throw ExceptionConstant.NOT_FOUND_ITEM;
        }
    }

    /**
     * count all
     *
     * @return long
     */
    @Override
    public long count() {
        return repository.count();
    }

    /**
     * save by domain
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    @Override
    public DOMAIN create(@NonNull DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");
        return save(domain);
    }

    /**
     * save by domains
     *
     * @param domains domains
     * @return List
     */
    @NonNull
    @Override
    public List<DOMAIN> createInBatch(@NonNull Collection<DOMAIN> domains) {
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() : repository.saveAll(domains);
    }

    /**
     * Updates by domain
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    @Override
    public DOMAIN update(@NonNull DOMAIN domain, ID id) {
        Assert.notNull(domain, domainName + " data must not be null");
        Assert.notNull(id, id + " data must not be null");
        DOMAIN entity = getById(id);
        SpringUtil.cloneWithoutNullVal(domain, entity);
        return save(entity);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    /**
     * Updates by domains
     *
     * @param domains domains
     * @return List
     */
    @NonNull
    @Override
    public List<DOMAIN> updateInBatch(@NonNull Collection<DOMAIN> domains) {
        return CollectionUtils.isEmpty(domains) ? Collections.emptyList() : repository.saveAll(domains);
    }

    /**
     * Removes by id
     *
     * @param id id
     * @return DOMAIN
     */
    @NonNull
    @Override
    public DOMAIN removeById(@NonNull ID id) {
        // Get non null domain by id
        DOMAIN domain = getById(id);

        // Remove it
        remove(domain);
        // return the deleted domain
        return domain;
    }

    /**
     * Removes by id if present.
     *
     * @param id id
     * @return DOMAIN
     */
    @Override
    public DOMAIN removeByIdOfNullable(@NonNull ID id) {
        return fetchById(id).map(domain -> {
            remove(domain);
            return domain;
        }).orElse(null);
    }

    /**
     * Remove by domain
     *
     * @param domain domain
     */
    @Override
    public void remove(@NonNull DOMAIN domain) {
        Assert.notNull(domain, domainName + " data must not be null");
        cache().delete(key(fetchClass(), domain.getId()));
        repository.delete(domain);
    }

    /**
     * Remove by ids
     *
     * @param ids ids
     */
    @Override
    public void removeInBatch(@NonNull Collection<ID> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            log.debug(domainName + " id collection is empty");
            return;
        }
        cache().delete(
            ids.stream()
                .map(id -> key(fetchClass(), id))
                .collect(Collectors.toList()));
        repository.deleteByIdIn(ids);
    }

    /**
     * Remove all by domains
     *
     * @param domains domains
     */
    @Override
    public void removeAll(@NonNull Collection<DOMAIN> domains) {
        if (CollectionUtils.isEmpty(domains)) {
            log.debug(domainName + " collection is empty");
            return;
        }

        cache().delete(
            domains.stream()
                .map(domain -> key(fetchClass(), domain.getId()))
                .collect(Collectors.toList()));
        repository.deleteInBatch(domains);
    }

    @Override
    public DOMAIN save(DOMAIN domain) {
        DOMAIN entity = repository.saveAndFlush(domain);
        refreshCache(entity);
        return entity;
    }

    @Override
    public void saveAll(Iterable<DOMAIN> domains) {
        repository.saveAll(domains).forEach(this::refreshCache);
    }


    /**
     * util methods
     */
    @Override
    public Class<?> getTemplateType() {
        return (Class<?>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];
    }


    /**
     * Fetch by id
     *
     * @param id id
     * @return Optional
     */
    @Override
    @SuppressWarnings("unchecked")
    public Optional<DOMAIN> fetchById(ID id) {
        Assert.notNull(id, domainName + " id must not be null");
        String key = key(fetchClass(), id);
        Object res;
        if ((res = parseObj(fetchClass(), id)) != null) {
            return Optional.of((DOMAIN) res);
        }
        res = repository.findById(id);
        cache().put(key, JSON.toJSONString(res));
        return (Optional<DOMAIN>) res;
    }

    private void refreshCache(DOMAIN entity) {
        cache().put(key(fetchClass(), entity.getId()), JSON.toJSONString(entity));
    }

    protected CacheStore<String, String> cache() {
        return handler.fetchCacheStore();
    }

    protected Object parseObj(Class<?> clazz, Object... args) {
        return JSON.parseObject(cache().get(key(clazz, args)), clazz);
    }

    protected String key(Class<?> clazz, Object args) {
        return CacheUtil.defaultKey(clazz, args, CachePrefix.ENTITY);
    }

    protected Class<?> fetchClass() {
        try {
            return Class.forName(fetchType().getTypeName());
        } catch (ClassNotFoundException e) {
            log.error("class[{}] not found, can't generator cache key, trace is {}",
                fetchType().getTypeName(), JSON.toJSONString(e.getStackTrace()));
            return null;
        }
    }

    /**
     * Gets actual generic type.
     *
     * @return real generic type will be returned
     */
    private Type fetchType() {
        return ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}