package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.Dict;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 13:53
 */
public interface DictRepository extends BaseRepository<Dict, Long> {
    /**
     * 依据type,key获得Dict实例
     *
     * @param type
     * @param key
     * @return
     */
    Dict findByTypeAndK(String type, String key);

    /**
     * 依据type获得Dict实例集合
     *
     * @param type
     * @return
     */
    List<Dict> findAllByType(String type);

    /**
     * 统计符合条件的列表数据全部个数
     *
     * @param type
     * @return
     */
    @Query(value = "select count(*) from dict t where if(?1!='',t.type like concat('%',?1,'%'),1=1)", nativeQuery = true)
    int count(String type);
}
