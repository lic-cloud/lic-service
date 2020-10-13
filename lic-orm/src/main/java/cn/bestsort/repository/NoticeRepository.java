package cn.bestsort.repository;

import cn.bestsort.model.entity.Notice;
import org.springframework.data.jpa.repository.Query;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 15:17
 */
public interface NoticeRepository extends BaseRepository<Notice, Long> {
    @Query(value = "select count(*) from t_notice t where if(?1!='',t.title like concat('%',?1,'%'),1=1) and if(IFNULL(?2,'')!='',t.status=?2,1=1) ", nativeQuery = true)
    int count(String title, Integer status);

    Notice findAllById(Long id);

}
