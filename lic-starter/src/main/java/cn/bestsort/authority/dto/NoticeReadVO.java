package cn.bestsort.authority.dto;


import cn.bestsort.authority.model.Notice;

import java.util.Date;

/**
 * @author GoodTime0313
 * @version 1.0
 * @date 2020/9/15 8:59
 */
public class NoticeReadVO extends Notice {

	private static final long serialVersionUID = -3842182350180882396L;

	private Long userId;
	private Date readTime;
	private Boolean isRead;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
}
