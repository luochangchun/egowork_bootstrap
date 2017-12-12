package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 文章对象
 * 
 * @author marker
 */
@Entity("human_resources")
public class Human_resources implements Serializable
{
	private static final long serialVersionUID = 7449373866719190815L;
	/** 文章ID */
	private Integer id;
	/** 所属栏目ID */
	private Integer cid;
	/** 图标 */
	private String icon;
	/** 标题 */
	private String title;
	/**
	 * 说明
	 */
	private String explain;
	/**
	 * 公司网站
	 */
	private String website;
	/** 关键字 */
	private String keywords;
	/**
	 * 优惠服务
	 */
	private String preferential;
	/** 内容 */
	private String content;
	/** 描述 */
	private String description;
	/**
	 * 服务详情介绍
	 */
	private String introduce;
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
	/**
	 * 申请补贴券
	 */
	private int tips;
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

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(final String icon)
	{
		this.icon = icon;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public String getExplain()
	{
		return explain;
	}

	public void setExplain(final String explain)
	{
		this.explain = explain;
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

	public String getPreferential()
	{
		return preferential;
	}

	public void setPreferential(final String preferential)
	{
		this.preferential = preferential;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public String getIntroduce()
	{
		return introduce;
	}

	public void setIntroduce(final String introduce)
	{
		this.introduce = introduce;
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

	public int getTips()
	{
		return tips;
	}

	public void setTips(final int tips)
	{
		this.tips = tips;
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
