package cn.bestsort.repository;

import cn.bestsort.model.entity.Role;
import org.springframework.data.jpa.repository.Query;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 14:58
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
    Role findFirstByName(String name);

    @Query(value = "select count(*) from sys_role t where if(?1!='',t.name like concat('%',?1,'%'),1=1)", nativeQuery = true)
    int count(String name);

    void deleteAllById(Long id);
}
