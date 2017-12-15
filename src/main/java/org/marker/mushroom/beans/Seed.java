package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 孵化项目
 * Created by 30 on 2017/12/14.
 */
@Entity("seed")
public class Seed implements Serializable {

	private Integer id;
	/** 项目名称 */
	private String name;
	/** 孵化资金 */
	private String funds;
	/** 项目简介 */
	private String intro;
	/** 项目图片 */
	private String icon;
	/** 添加时间 */
	private Date time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
