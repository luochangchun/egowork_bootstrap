package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户在egowork中注册
 *
 * @author dd
 */
@Entity("user_regist_log")
public class User_regist_log implements Serializable {

	private static final long serialVersionUID = -7949257515592105170L;
	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 注册时手机号
	 */
	private String phone;
	/**
	 * 日期
	 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}
}
