package cn.bestsort.repository;

import cn.bestsort.model.entity.User;
import org.springframework.data.jpa.repository.Query;

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
    @Query(value = "select count(*) from user t where if(?1!='',t.username like concat('%',?1,'%'),1=1)  and if(?2!='',t.nickname like concat('%',?2,'%'),1=1) and if(IFNULL(?3,'') !='',t.status=?3,1=1)", nativeQuery = true)
    int count(String username, String nickname, Integer status);

    /**
     * 根据手机号获取用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据电话获取用户
     * @param telephone
     * @return
     */
    User findFirstByTelephone(String telephone);

    /**
     * 根据邮件获取用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据用户名和邮箱获取用户
     * @param username
     * @param email
     * @return
     */
    User findByUsernameAndAndEmail(String username,String email);

    /**
     * 查询满足任意条件的用户集合
     * @param username
     * @param phone
     * @param telephone
     * @param email
     * @return
     */
    List<User> findAllByUsernameOrPhoneOrTelephoneOrEmail(String username, String phone, String telephone, String email);
}
