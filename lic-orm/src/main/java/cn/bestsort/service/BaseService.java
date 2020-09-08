package cn.bestsort.service;

import java.util.Collection;
import java.util.List;

import cn.bestsort.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-08 20:23
 */

@Transactional(rollbackFor = Exception.class)
public interface BaseService<DOMAIN extends BaseEntity, ID> {
    /**
     * List All
     *
     * @return List
     */
    List<DOMAIN> listAll();

    /**
     * List all by sort
     *
     * @param sort sort
     * @return List
     */
    @NonNull
    List<DOMAIN> listAll(@NonNull Sort sort);

    /**
     * List all by pageable
     *
     * @param pageable pageable
     * @return Page
     */
    @NonNull
    Page<DOMAIN> listAll(@NonNull Pageable pageable);

    /**
     * List all by ids
     *
     * @param ids ids
     * @return List
     */
    @NonNull
    List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids);

    /**
     * List all by ids and sort
     *
     * @param ids  ids
     * @param sort sort
     * @return List
     */
    @NonNull
    List<DOMAIN> listAllByIds(@Nullable Collection<ID> ids, @NonNull Sort sort);

    /**
     * Get by id
     *
     * @param id id
     * @return DOMAIN
     */
    @NonNull
    DOMAIN getById(@NonNull ID id);

    @NonNull
    Page<DOMAIN> getByIds(@NonNull Collection<ID> ids, Pageable page);

    /**
     * Gets domain of nullable by id.
     *
     * @param id id
     * @return DOMAIN
     */
    @Nullable
    DOMAIN getByIdOfNullable(@NonNull ID id);

    /**
     * Exists by id.
     *
     * @param id id
     * @return boolean
     */
    boolean existsById(@NonNull ID id);

    /**
     * Must exist by id, or throw NotFoundException.
     *
     * @param id id
     */
    void mustExistById(@NonNull ID id);

    /**
     * count all
     *
     * @return long
     */
    long count();

    /**
     * save by domain
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    DOMAIN create(@NonNull DOMAIN domain);

    /**
     * save by domains
     *
     * @param domains domains
     * @return List
     */
    @NonNull
    List<DOMAIN> createInBatch(@NonNull Collection<DOMAIN> domains);

    /**
     * Updates by domain id
     *
     * @param domain domain
     * @return DOMAIN
     */
    @NonNull
    DOMAIN update(@NonNull DOMAIN domain, ID id);

    /**
     * Flushes all pending changes to the database.
     */
    void flush();

    /**
     * Updates by domains
     *
     * @param domains domains
     * @return List
     */
    @NonNull
    List<DOMAIN> updateInBatch(@NonNull Collection<DOMAIN> domains);

    /**
     * Removes by id
     *
     * @param id id
     * @return DOMAIN
     */
    @NonNull
    DOMAIN removeById(@NonNull ID id);

    /**
     * Removes by id if present.
     *
     * @param id id
     * @return DOMAIN
     */
    @Nullable
    DOMAIN removeByIdOfNullable(@NonNull ID id);

    /**
     * Remove by domain
     *
     * @param domain domain
     */
    void remove(@NonNull DOMAIN domain);

    /**
     * Remove by ids
     *
     * @param ids ids
     */
    void removeInBatch(@NonNull Collection<ID> ids);

    /**
     * Remove all by domains
     *
     * @param domains domains
     */
    void removeAll(@NonNull Collection<DOMAIN> domains);


    /**
     * save
     *
     * @param domain must be not null
     * @return
     */
    DOMAIN save(DOMAIN domain);

    /**
     * save all
     *
     * @param set data
     */
    void saveAll(Iterable<DOMAIN> set);

    /**
     * get class impl' supported type
     *
     * @return clazz
     */
    Class<?> getTemplateType();
}

