package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 共享会议室预订申请
 * Created by 30 on 2017/12/14.
 */
@Entity("room_apply")
public class RoomApply implements Serializable {

	private Integer id;
	/** 申请企业 */
	private String enterprise;
	/** 联系人 */
	private String contact;
	/** 手机号 */
	private String phone;
	/** 会议主题 */
	private String subject;
	/** 会议地址id（字典） */
	private int addrId;
	/** 会议地址 */
	private String addr;
	/** 开始时间 */
	private Date from;
	/** 结束时间 */
	private Date to;
	/** 用途id（字典） */
	private int usageId;
	/** 用途 */
	private String usage;
	/** 参加人数 */
	private int attender;
	/** 会议需求 */
	private String request;
	/** 添加时间 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getAddrId() {
		return addrId;
	}

	public void setAddrId(int addrId) {
		this.addrId = addrId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public int getUsageId() {
		return usageId;
	}

	public void setUsageId(int usageId) {
		this.usageId = usageId;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public int getAttender() {
		return attender;
	}

	public void setAttender(int attender) {
		this.attender = attender;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
