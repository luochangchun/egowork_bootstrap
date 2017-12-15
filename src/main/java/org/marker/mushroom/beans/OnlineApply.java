package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 线上孵化申请
 * Created by 30 on 2017/12/14.
 */
@Entity("online_apply")
public class OnlineApply implements Serializable {

	private Integer id;
	/** 联系人 */
	private String contact;
	/** 手机号 */
	private String phone;
	/** 企业名称 */
	private String enterprise;
	/** 注册时间 */
	private Date signup;
	/** 注册地址 */
	private String signupAddr;
	/** 经营范围及主营业务 */
	private String business;
	/** 企业性质id（字典） */
	private int substanceId;
	/** 企业性质 */
	private String substance;
	/** 在职人数 */
	private int employee;
	/** 注册资金 */
	private String capital;
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

	public String getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(String enterprise) {
		this.enterprise = enterprise;
	}

	public Date getSignup() {
		return signup;
	}

	public void setSignup(Date signup) {
		this.signup = signup;
	}

	public String getSignupAddr() {
		return signupAddr;
	}

	public void setSignupAddr(String signupAddr) {
		this.signupAddr = signupAddr;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public int getSubstanceId() {
		return substanceId;
	}

	public void setSubstanceId(int substanceId) {
		this.substanceId = substanceId;
	}

	public String getSubstance() {
		return substance;
	}

	public void setSubstance(String substance) {
		this.substance = substance;
	}

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
