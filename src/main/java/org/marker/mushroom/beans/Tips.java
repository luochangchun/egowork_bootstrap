package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 补贴券
 *
 * @author dd
 */
@Entity("tips")
public class Tips implements Serializable {

	private static final long serialVersionUID = 1661046246773289468L;

	/**
	 * 补贴券id
	 */
	private Integer id;
	/**
	 * 选择单位id
	 */
	private Integer hrId;
	/**
	 * 分类id
	 */
	private Integer cid;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 用户名称
	 */
	private String userName;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 时间
	 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public Integer getHrId() {
		return hrId;
	}

	public void setHrId(final Integer hrId) {
		this.hrId = hrId;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(final Integer cid) {
		this.cid = cid;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}
}
