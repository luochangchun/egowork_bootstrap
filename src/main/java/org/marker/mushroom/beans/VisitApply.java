package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约参观申请
 * Created by 30 on 2017/12/14.
 */
@Entity("visit_apply")
public class VisitApply implements Serializable {

	private Integer id;
	/** 联系人 */
	private String contact;
	/** 手机号 */
	private String phone;
	/** 孵化器id */
	private int incubatorId;
	/** 孵化器名 */
	private String incubator;
	/** 参观时间 */
	private Date visitAt;
	/** 感兴趣产品id（字典） */
	private int interestId;
	/** 感兴趣产品 */
	private String interest;
	/** 添加时间 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public int getIncubatorId() {
		return incubatorId;
	}

	public void setIncubatorId(int incubatorId) {
		this.incubatorId = incubatorId;
	}

	public String getIncubator() {
		return incubator;
	}

	public void setIncubator(String incubator) {
		this.incubator = incubator;
	}

	public Date getVisitAt() {
		return visitAt;
	}

	public void setVisitAt(Date visitAt) {
		this.visitAt = visitAt;
	}

	public int getInterestId() {
		return interestId;
	}

	public void setInterestId(int interestId) {
		this.interestId = interestId;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
