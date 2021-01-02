package cn.bestsort.repository;

import cn.bestsort.model.entity.Role;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-17 14:58
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
    /**
     * 获得第一个符合name的角色
     *
     * @param name
     * @return
     */
    Role findFirstByName(String name);

    /**
     * 统计符合条件的列表数据全部个数
     *
     * @param name
     * @return
     */
    @Query(value = "select count(*) from role t where if(?1!='',t.name like concat('%',?1,'%'),1=1)", nativeQuery = true)
    int count(String name);

    /**
     * 依据id删除角色
     *
     * @param id
     */
    void deleteAllById(Long id);
}
