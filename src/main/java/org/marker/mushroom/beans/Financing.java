package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 融资贷款
 * 
 * @author dd
 */
@Entity("financing")
public class Financing implements Serializable
{
	private static final long serialVersionUID = 7455804026919111943L;
	/** 文章ID */
	private Integer id;
	/** 所属栏目ID */
	private Integer cid;
	/** 标题 */
	private String title;
	/** 封面图标 */
	private String icon;
	/**
	 * 封面说明
	 */
	private String explain;
	/**
	 * 标签
	 */
	private String label;
	/**
	 * 公司网站
	 */
	private String website;
	/** 关键字 */
	private String keywords;
	/**
	 * 企业名称
	 */
	private String ename;
	/**
	 * 联系人
	 */
	private String contacts;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 项目名称
	 */
	private String entryName;
	/**
	 * 项目优势
	 */
	private String advantage;
	/**
	 * 融资
	 */
	private String amount;
	/**
	 * 融资进度
	 */
	private String schedule;
	/** 作者 */
	private String author;
	/** 发布时间 */
	private Date time;
	/** 浏览量 */
	private int views;
	/* 发布状态：0草稿 1发布 */
	private int status;
	/** 内容类型 */
	private int type;
	/** 原始内容 */
	private String orginal;
	/** 创建时间 **/
	private Date createTime;

	private String userId;

	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer id)
	{
		this.id = id;
	}

	public Integer getCid()
	{
		return cid;
	}

	public void setCid(final Integer cid)
	{
		this.cid = cid;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(final String icon)
	{
		this.icon = icon;
	}

	public String getExplain()
	{
		return explain;
	}

	public void setExplain(final String explain)
	{
		this.explain = explain;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(final String label)
	{
		this.label = label;
	}

	public String getWebsite()
	{
		return website;
	}

	public void setWebsite(final String website)
	{
		this.website = website;
	}

	public String getKeywords()
	{
		return keywords;
	}

	public void setKeywords(final String keywords)
	{
		this.keywords = keywords;
	}

	public String getEname()
	{
		return ename;
	}

	public void setEname(final String ename)
	{
		this.ename = ename;
	}

	public String getContacts()
	{
		return contacts;
	}

	public void setContacts(final String contacts)
	{
		this.contacts = contacts;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(final String address)
	{
		this.address = address;
	}

	public String getEntryName()
	{
		return entryName;
	}

	public void setEntryName(final String entryName)
	{
		this.entryName = entryName;
	}

	public String getAdvantage()
	{
		return advantage;
	}

	public void setAdvantage(final String advantage)
	{
		this.advantage = advantage;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(final String amount)
	{
		this.amount = amount;
	}

	public String getSchedule()
	{
		return schedule;
	}

	public void setSchedule(final String schedule)
	{
		this.schedule = schedule;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(final String author)
	{
		this.author = author;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public int getViews()
	{
		return views;
	}

	public void setViews(final int views)
	{
		this.views = views;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(final int status)
	{
		this.status = status;
	}

	public int getType()
	{
		return type;
	}

	public void setType(final int type)
	{
		this.type = type;
	}

	public String getOrginal()
	{
		return orginal;
	}

	public void setOrginal(final String orginal)
	{
		this.orginal = orginal;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(final Date createTime)
	{
		this.createTime = createTime;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(final String userId)
	{
		this.userId = userId;
	}
}
