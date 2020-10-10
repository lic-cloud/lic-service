package cn.bestsort.repository.impl;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.model.entity.Notice;
import cn.bestsort.model.entity.Role;
import cn.bestsort.model.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Time;
import java.util.List;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/10/9 9:34
 */
@Repository
public class RepositoryEntity {

    @PersistenceContext
    private EntityManager entityManager;
    StringBuffer sql;
    public List<User> listUser(String username, String nickname, Integer status, String orderBy, Integer offset, Integer limit) {

        if (status==null){
            sql = new StringBuffer("select * from sys_user t where (t.username like '"+username+"' or '"+username+"'='') and " +
                "(t.nickname like '"+nickname+"' or '"+nickname+"'='')  " + orderBy + " limit " + offset + "," + limit + "");
        }else{
            sql = new StringBuffer("select * from sys_user t where (t.username like '"+username+"' or '"+username+"'='') and " +
                "(t.nickname like '"+nickname+"' or '"+nickname+"'='') and " +
                " t.status = "+status+"  " + orderBy + " limit " + offset + "," + limit + "");
        }
        List<User> resultList = entityManager.createNativeQuery(sql.toString(), User.class).getResultList();
        return resultList;
    }

    public List<Role> listRole(String name, String orderBy, Integer offset, Integer limit) {
         sql = new StringBuffer("select * from sys_role t where (t.name like '"+name+"' or '"+name+"'='') " + orderBy + " limit " + offset + "," + limit + "");
        List<Role> resultList = entityManager.createNativeQuery(sql.toString(), Role.class).getResultList();
        return resultList;
    }

    public List<Dict> listDict(String type, String orderBy, Integer offset, Integer limit) {
         sql = new StringBuffer("select * from t_dict t where (t.type like '"+type+"' or '"+type+"'='') " + orderBy + " limit " + offset + "," + limit + "");
        List<Dict> resultList = entityManager.createNativeQuery(sql.toString(), Dict.class).getResultList();
        return resultList;
    }

    public List<Notice> listNotice(String title, Integer status , String orderBy, Integer offset, Integer limit) {
        if (status==null){
            sql = new StringBuffer("select * from t_notice t where" +
                " (t.title like '"+title+"' or '"+title+"'='')  " + orderBy + " limit " + offset + "," + limit + "");
        }else{
            sql = new StringBuffer("select * from t_notice t where" +
                " (t.title like '"+title+"' or '"+title+"'='') and " +
                " t.status = "+status+" " + orderBy + " limit " + offset + "," + limit + "");
        }
        List<Notice> resultList = entityManager.createNativeQuery(sql.toString(), Notice.class).getResultList();
        return resultList;
    }
}
