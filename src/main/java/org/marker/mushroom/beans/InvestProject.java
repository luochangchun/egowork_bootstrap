package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 30 on 2018/1/3 0003.
 * 融资项目模块
 */
@Entity("invest_project")
public class InvestProject implements Serializable {

	/**id*/
	private Integer id;
	/**审核状态*，0待审核，1通过，2驳回*/
	private Integer status;
	/**项目名称*/
	private String name;
	/**公司名称*/
	private String enterpirse;
	/**项目优势*/
	private String advantage;
	/**融资金额*/
	private String capital;
	/**融资进度*/
	private String progress;
	/**行业领域*/
	private String field;
	/**联系人*/
	private String contact;
	/**联系电话*/
	private String phone;
	/**邮箱*/
	private String email;
	/**公司简介*/
	private String intro;
	/** 申请时间 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnterpirse() {
		return enterpirse;
	}

	public void setEnterpirse(String enterpirse) {
		this.enterpirse = enterpirse;
	}

	public String getAdvantage() {
		return advantage;
	}

	public void setAdvantage(String advantage) {
		this.advantage = advantage;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
