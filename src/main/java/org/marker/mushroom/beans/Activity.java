package org.marker.mushroom.beans;

import java.io.Serializable;
import java.util.Date;

import org.marker.mushroom.dao.annotation.Entity;


/**
 * 文章对象
 * 
 * @author marker
 */
@Entity("activity")
public class Activity implements Serializable
{

	private static final long serialVersionUID = -3081020382452658418L;
	/** 文章ID */
	private Integer id;
	/** 所属栏目ID */
	private Integer cid;
	/** 标题 */
	private String title;
	/** 图标 */
	private String icon;
	/** 内容 */
	private String content;
	/** 地址 **/
	private String address;
	/** 人数限制 **/
	private Integer peopleLimit;
	/** 票种 **/
	private String ticket;
	/** 电话 **/
	private String phone;
	/** 活动类型 **/
	private Integer type;
	/** 联系人 */
	private String author;
	/** 浏览量 */
	private Integer views;
	/**
	 * 活动时间
	 */
	private Date activeTime;
	/** 发布时间 */
	private Date time;
	/* 发布状态：0草稿 1发布 */
	private Integer status;
	/** 创建时间 **/
	private Date createTime;
	/** 用户id **/
	private String userId;
	/** 留言id **/
	private Integer gbid;

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

	public String getContent()
	{
		return content;
	}

	public void setContent(final String content)
	{
		this.content = content;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(final String address)
	{
		this.address = address;
	}

	public Integer getPeopleLimit()
	{
		return peopleLimit;
	}

	public void setPeopleLimit(final Integer peopleLimit)
	{
		this.peopleLimit = peopleLimit;
	}

	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(final String ticket)
	{
		this.ticket = ticket;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(final Integer type)
	{
		this.type = type;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(final String author)
	{
		this.author = author;
	}

	public Integer getViews()
	{
		return views;
	}

	public void setViews(final Integer views)
	{
		this.views = views;
	}

	public Date getActiveTime()
	{
		return activeTime;
	}

	public void setActiveTime(final Date activeTime)
	{
		this.activeTime = activeTime;
	}

	public Date getTime()
	{
		return time;
	}

	public void setTime(final Date time)
	{
		this.time = time;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(final Integer status)
	{
		this.status = status;
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

	public Integer getGbid()
	{
		return gbid;
	}

	public void setGbid(final Integer gbid)
	{
		this.gbid = gbid;
	}
}
