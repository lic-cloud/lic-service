//package cn.bestsort.authority.dao;
//
//import org.apache.ibatis.annotations.Mapper;
//
///**
// * @author GoodTime0313
// * @version 1.0
// * @date 2020/9/15 8:59
// */
//@Mapper
//public interface PermissionDao {
//
//    //@Select("select * from sys_permission t where t.type = 1 order by t.sort")
////    List<Permission> listParents();
//    //
//    //@Select("select distinct p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId inner join sys_role_user ru on ru.roleId = rp.roleId where ru.userId = #{userId} order by p.sort")
//    //List<Permission> listByUserId(Long userId);
//    //
//    //@Select("select p.* from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
//    //List<Permission> listByRoleId(Long roleId);
//
//
//
//}
