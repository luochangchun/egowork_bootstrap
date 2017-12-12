package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 我要报名
 *
 * @author dd
 * @version 1.0
 */
@Entity("apply_activity")
public class Apply_activity implements Serializable
{

	private static final long serialVersionUID = 4339851351183579192L;
	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 活动id
	 */
	private Integer activityId;
	/**
	 * 活动名称
	 */
	private String title;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 时间
	 */
	private Date time;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 登陆用户id
	 */
	private Integer userId;

	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public Integer getActivityId()
	{
		return activityId;
	}

	public void setActivityId(final Integer activityId)
	{
		this.activityId = activityId;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public String getCompany()
	{
		return company;
	}

	public void setCompany(final String company)
	{
		this.company = company;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(final Date createTime)
	{
		this.createTime = createTime;
	}

	public Integer getUserId()
	{
		return userId;
	}

	public void setUserId(final Integer userId)
	{
		this.userId = userId;
	}
}
