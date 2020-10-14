package cn.bestsort.repository;

import cn.bestsort.model.entity.Notice;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 15:17
 */
public interface NoticeRepository extends BaseRepository<Notice, Long> {
    /**
     * 统计符合条件的列表数据全部个数
     *
     * @param title
     * @param status
     * @return
     */
    @Query(value = "select count(*) from t_notice t where if(?1!='',t.title like concat('%',?1,'%'),1=1) and if(IFNULL(?2,'')!='',t.status=?2,1=1) ", nativeQuery = true)
    int count(String title, Integer status);

    /**
     * 依据id查询获得通知实例
     *
     * @param id
     * @return
     */
    Notice findAllById(Long id);

}
