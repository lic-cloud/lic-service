package cn.bestsort.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.bestsort.model.entity.Dict;
import cn.bestsort.model.entity.Notice;
import cn.bestsort.model.entity.Role;
import cn.bestsort.model.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/10/9 9:34
 */
@Repository
@SuppressWarnings("unchecked")
public class RepositoryEntity {
    private static final String PAGE_SQL = " %s limit %s,%s";
    private static final String LIST_USER_WITHOUT_STATUS_SQL = "select * from sys_user t where " +
        "(t.username like '%s' or '%s'='') and (t.nickname like '%s' or '%s'='')";

    private static final String LIST_USER_WITH_STATUS_SQL = "select * from sys_user t where (t.username like '%s' or '%s'='') and " +
        "(t.nickname like '%s' or '%s'='') and " + "t.status = %s";

    private static final String LIST_ROLE_SQL = "select * from sys_role t where (t.name like '%s' or '%s'='') ";
    private static final String LIST_DICT_SQL = "select * from t_dict t where (t.type like '%s' or '%s'='') ";

    private static final String LIST_NOTICE_WITHOUT_STATUS_SQL = "select * from t_notice t where (t.title like '%s' or '%s'='')";

    private static final String LIST_NOTICE_SQL = "select * from t_notice t where" +
        " (t.title like '%s' or '%s'='') and t.status = '%s'";

    @PersistenceContext
    private EntityManager entityManager;

    private String page(String sql, String orderBy, Integer offset, Integer limit) {
        return String.format(sql + PAGE_SQL, orderBy, offset, limit);
    }
    public List<User> listUser(String username, String nickname, Integer status, String orderBy, Integer offset, Integer limit) {
        String sql = status == null ?
            String.format(LIST_USER_WITHOUT_STATUS_SQL, username, username, nickname, nickname) :
            String.format(LIST_USER_WITH_STATUS_SQL, username, username, nickname, nickname, status);
        return entityManager.createNativeQuery(
            page(sql, orderBy, offset, limit), User.class).getResultList();
    }

    public List<Role> listRole(String name, String orderBy, Integer offset, Integer limit) {
        String sql = String.format(LIST_ROLE_SQL, name, name);
        List<Role> resultList = entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Role.class).getResultList();
        return resultList;
    }

    public List<Dict> listDict(String type, String orderBy, Integer offset, Integer limit) {
        String sql = String.format(LIST_DICT_SQL, type, type);
        List<Dict> resultList = entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Dict.class).getResultList();
        return resultList;
    }

    public List<Notice> listNotice(String title, Integer status, String orderBy, Integer offset, Integer limit) {
        String sql = status == null ?
            String.format(LIST_NOTICE_WITHOUT_STATUS_SQL, title, title) :
            String.format(LIST_NOTICE_SQL, title, title, status);
        List<Notice> resultList = entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Notice.class).getResultList();
        return resultList;
    }
}
