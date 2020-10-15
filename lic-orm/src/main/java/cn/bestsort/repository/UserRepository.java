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
    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 统计符合条件的列表数据全部个数
     *
     * @param username
     * @param nickname
     * @param status
     * @return
     */
    @Query(value = "select count(*) from sys_user t where if(?1!='',t.username like concat('%',?1,'%'),1=1)  and if(?2!='',t.nickname like concat('%',?2,'%'),1=1) and if(IFNULL(?3,'') !='',t.status=?3,1=1)", nativeQuery = true)
    int count(String username, String nickname, Integer status);

    /**
     * 根据id集合查询用户
     *
     * @param lists
     * @return
     */
    List<User> findAllByIdIn(Collection lists);
}
