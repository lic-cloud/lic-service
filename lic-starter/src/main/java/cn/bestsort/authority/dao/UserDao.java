package cn.bestsort.authority.dao;

import cn.bestsort.authority.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
@Mapper
public interface UserDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nickname, headImgUrl, phone, telephone, email, birthday, sex, status, createTime, updateTime) values(#{username}, #{password}, #{nickname}, #{headImgUrl}, #{phone}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, now(), now())")
	int save(User user);

	@Select("select * from sys_user t where t.id = #{id}")
	User getById(Long id);

	@Select("select * from sys_user t where t.username = #{username}")
	User getUser(String username);

	@Update("update sys_user t set t.password = #{password} where t.id = #{id}")
	int changePassword(@Param("id") Long id, @Param("password") String password);

	Integer count(@Param("params") Map<String, Object> params);

	List<User> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
			@Param("limit") Integer limit);

	@Delete("delete from sys_role_user where userId = #{userId}")
	int deleteUserRole(Long userId);

	int saveUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

	int update(User user);
}
