package cn.bestsort.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cn.bestsort.model.entity.*;
import cn.bestsort.model.vo.NoticeReadVO;
import cn.bestsort.repository.NoticeReadRepository;
import cn.bestsort.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String LIST_NOTICE_SQL = "select * from t_notice t where (t.title like '%s' or '%s'='') and t.status = '%s'";
    private static final String LIST_READ_USERS = "select  u.* from t_notice_read r inner join sys_user u on u.id = r.user_id where r.notice_id = '%s' order by r.create_at desc";
    private static final String LIST_NOTICE = "select t.*,r.user_id from t_notice t left join t_notice_read r on r.notice_id = t.id and r.user_id = '%s' where t.status = 1 and (t.title like '%s' or '%s'='') ";
    /*
     *  and IF('%s' = null and '%s' = '','',IF('%s'=0,r.create_at is null , r.create_at is not null ))
     *
     */

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private NoticeReadRepository noticeReadRepository;

    private String page(String sql, String orderBy, Integer offset, Integer limit) {
        return String.format(sql + PAGE_SQL, orderBy, offset, limit);
    }

    public List<User> listUser(String username, String nickname, Integer status, String orderBy, Integer offset, Integer limit) {
        String sql = status == null ?
            String.format(LIST_USER_WITHOUT_STATUS_SQL, username, username, nickname, nickname) :
            String.format(LIST_USER_WITH_STATUS_SQL, username, username, nickname, nickname, status);
        return entityManager.createNativeQuery(page(sql, orderBy, offset, limit), User.class).getResultList();
    }

    public List<Role> listRole(String name, String orderBy, Integer offset, Integer limit) {
        String sql = String.format(LIST_ROLE_SQL, name, name);
        return (List<Role>) entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Role.class).getResultList();
    }

    public List<Dict> listDict(String type, String orderBy, Integer offset, Integer limit) {
        String sql = String.format(LIST_DICT_SQL, type, type);
        return (List<Dict>) entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Dict.class).getResultList();
    }

    public List<User> listReadUsers(Long noticeId) {
        String sql = String.format(LIST_READ_USERS, noticeId);
        return (List<User>) entityManager.createNativeQuery(sql, User.class).getResultList();
    }


    public List<Notice> listNotice(String title, Integer status, String orderBy, Integer offset, Integer limit) {
        String sql = status == null ?
            String.format(LIST_NOTICE_WITHOUT_STATUS_SQL, title, title) :
            String.format(LIST_NOTICE_SQL, title, title, status);
        return (List<Notice>) entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Notice.class).getResultList();
    }

    public List<NoticeReadVO> listNoticeRead(Integer userId, String title, Integer isRead, String orderBy, Integer offset, Integer limit) {
        String sql = String.format(LIST_NOTICE, userId, title, title);
        List<NoticeReadVO> vos = new ArrayList<>();
        List<Notice> resultList = entityManager.createNativeQuery(page(sql, orderBy, offset, limit), Notice.class).getResultList();
        for (Notice i : resultList) {
            NoticeReadVO vo = new NoticeReadVO();
            SpringUtil.cloneWithoutNullVal(i, vo);
            List<NoticeRead> all = noticeReadRepository.findAllByUserIdAndNoticeId((long) userId, i.getId());
            vo.setIsRead(!all.isEmpty());
            vo.setUserId((long) userId);
            vo.setReadTime(i.getCreateAt());
            vos.add(vo);
        }
        return vos;
    }

}
