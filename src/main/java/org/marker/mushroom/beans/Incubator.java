package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongbin on 2017/5/18.
 */
@Entity("incubator")
public class Incubator implements Serializable {

	private static final long serialVersionUID = 7474752827652178075L;

	private Integer id;
	private Integer cid;
	private String title;
	private String icon;
	private String name;
	private String contact;
	private String phone;
	private String address;
	private String level;
	private String pattern;
	private String region;
	private Integer isFree;
	private Integer type;
	private Integer area;
	private Integer enterprises;
	private Date time;
	private Date createTime;
	private String author;
	private Integer userId;
	private Integer status;
	private String types;
	private String settled;
	private String views;
	private Double station;
	private Double office;
	private String label;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(final Integer cid) {
		this.cid = cid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(final String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(final String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(final String level) {
		this.level = level;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(final String region) {
		this.region = region;
	}

	public Integer getIsFree() {
		return isFree;
	}

	public void setIsFree(final Integer isFree) {
		this.isFree = isFree;
	}

	public Integer getType() {
		return type;
	}

	public void setType(final Integer type) {
		this.type = type;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(final Integer area) {
		this.area = area;
	}

	public Integer getEnterprises() {
		return enterprises;
	}

	public void setEnterprises(final Integer enterprises) {
		this.enterprises = enterprises;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(final Date time) {
		this.time = time;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(final Integer userId) {
		this.userId = userId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(final Integer status) {
		this.status = status;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(final String types) {
		this.types = types;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(final String settled) {
		this.settled = settled;
	}

	public String getViews() {
		return views;
	}

	public void setViews(final String views) {
		this.views = views;
	}

	public Double getStation() {
		return station;
	}

	public void setStation(final Double station) {
		this.station = station;
	}

	public Double getOffice() {
		return office;
	}

	public void setOffice(final Double office) {
		this.office = office;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}
}

