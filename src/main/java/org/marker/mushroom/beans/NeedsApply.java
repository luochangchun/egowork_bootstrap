package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务需求申请
 * Created by 30 on 2017/12/19.
 */
@Entity("needs_apply")
public class NeedsApply implements Serializable {

	private Integer id;
	/** 需求分类id（字典） */
	private int classifyId;
	/** 需求分类 */
	private String classify;
	/** 企业名称 */
	private String enterprise;
	/** 联系人 */
	private String contact;
	/** 手机号 */
	private String phone;
	/** 需求标题 */
	private String title;
	/** 需求内容 */
	private String needs;
	/** 添加时间 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(int classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNeeds() {
		return needs;
	}

	public void setNeeds(String needs) {
		this.needs = needs;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
