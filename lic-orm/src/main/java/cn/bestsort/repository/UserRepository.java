package cn.bestsort.repository;

import cn.bestsort.model.entity.User;
import org.springframework.data.jpa.repository.Query;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020-09-09 11:10
 */
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "select count(*) from sys_user t where if(?1!='',t.username like concat('%',?1,'%'),1=1)  and if(?2!='',t.nickname like concat('%',?2,'%'),1=1) and if(IFNULL(?3,'') !='',t.status=?3,1=1)", nativeQuery = true)
    int count(String username, String nickname, Integer status);

    // 在排序时出错-> 使用UserRepositoryEntity实现
/*  @Query(value = "select * from sys_user t where if(:username !='',t.username like concat('%',:username,'%'),1=1)  and if(:nickname!='',t.nickname like concat('%',:nickname,'%'),1=1) and if(IFNULL(:status,'') !='',t.status=:status,1=1) :orderBy limit :offset,:limit", nativeQuery = true)
    List<User> listUser(@Param("username") String username,@Param("nickname") String nickname,@Param("status") Integer status,@Param("orderBy") String orderBy,@Param("offset") Integer offset,@Param("limit") Integer limit);*/
}
