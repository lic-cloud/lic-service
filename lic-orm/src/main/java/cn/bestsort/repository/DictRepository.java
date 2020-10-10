package cn.bestsort.repository;

import java.util.List;

import cn.bestsort.model.entity.Dict;
import org.springframework.data.jpa.repository.Query;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 13:53
 */
public interface DictRepository extends BaseRepository<Dict, Long> {
    Dict findByTypeAndK(String type, String key);
    List<Dict> findAllByType(String type);
    @Query(value = "select count(*) from t_dict t where if(?1!='',t.type like concat('%',?1,'%'),1=1)", nativeQuery = true)
    int count(String type);
}
