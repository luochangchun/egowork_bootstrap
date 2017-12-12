package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 申请成为服务商
 *
 * @author dd
 * @version 1.0
 */
@Entity("apply_service")
public class Apply_service implements Serializable
{

	private static final long serialVersionUID = 78605547470034885L;
	/**
	 * 序号
	 */
	private Integer id;
	/**
	 * 公司名称
	 */
	private String company;
	/**
	 * 主营业务
	 */
	private String business;
	/**
	 * 联系人
	 */
	private String contact;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 公司介绍
	 */
	private String introduction;
	/**
	 * 优惠服务描述
	 */
	private String preferential;
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

	public String getCompany()
	{
		return company;
	}

	public void setCompany(final String company)
	{
		this.company = company;
	}

	public String getBusiness()
	{
		return business;
	}

	public void setBusiness(final String business)
	{
		this.business = business;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(final String contact)
	{
		this.contact = contact;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getIntroduction()
	{
		return introduction;
	}

	public void setIntroduction(final String introduction)
	{
		this.introduction = introduction;
	}

	public String getPreferential()
	{
		return preferential;
	}

	public void setPreferential(final String preferential)
	{
		this.preferential = preferential;
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
