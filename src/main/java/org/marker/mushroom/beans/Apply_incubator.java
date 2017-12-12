package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 孵化器申请加入
 * 
 * @author dd
 * @version 1.0
 */
@Entity("apply_incubator")
public class Apply_incubator implements Serializable
{


	private static final long serialVersionUID = -4945189011346001356L;

	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 孵化器名称
	 */
	private String title;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 团队人数
	 */
	private Integer number;
	/**
	 * 公司简介
	 */
	private String introduction;
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

	public String getCompany()
	{
		return company;
	}

	public void setCompany(final String company)
	{
		this.company = company;
	}

	public Integer getNumber()
	{
		return number;
	}

	public void setNumber(final Integer number)
	{
		this.number = number;
	}

	public String getIntroduction()
	{
		return introduction;
	}

	public void setIntroduction(final String introduction)
	{
		this.introduction = introduction;
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
