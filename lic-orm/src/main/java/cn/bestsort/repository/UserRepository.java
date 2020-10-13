package cn.bestsort.repository;

import cn.bestsort.model.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-09 11:10
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "select count(*) from sys_user t where if(?1!='',t.username like concat('%',?1,'%'),1=1)  and if(?2!='',t.nickname like concat('%',?2,'%'),1=1) and if(IFNULL(?3,'') !='',t.status=?3,1=1)", nativeQuery = true)
    int count(String username, String nickname, Integer status);

    List<User> findAllByIdIn(Collection lists);
}
