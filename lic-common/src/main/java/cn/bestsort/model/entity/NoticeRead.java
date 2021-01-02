package cn.bestsort.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-09-17 19:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "t_notice_read")
public class NoticeRead extends BaseEntity {
    @Column
    Long userId;

    @Column
    Long noticeId;
    public static NoticeRead of(long user, long notice) {
        NoticeRead noticeRead = new NoticeRead();
        noticeRead.noticeId = notice;
        noticeRead.userId = user;
        return noticeRead;
    }
}
