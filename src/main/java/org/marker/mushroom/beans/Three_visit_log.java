package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 第三方访问
 *
 * @author dd
 */
@Entity("three_visit_log")
public class Three_visit_log implements Serializable {

	private static final long serialVersionUID = -286044783950500542L;

	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 登陆访问用户名
	 */
	private String name;
	/**
	 * 访问第三方地址
	 */
	private String url;
	/**
	 * 日期
	 */
	private Date time;
	/**
	 * 登陆访问用户id
	 */
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}
}
