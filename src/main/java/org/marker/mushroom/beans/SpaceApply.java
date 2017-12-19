package org.marker.mushroom.beans;

import org.marker.mushroom.dao.annotation.Entity;

import java.io.Serializable;

/**
 * Created by 30 on 2017/12/18 0018.
 * 租赁空间和众创空间
 */
@Entity("space_apply")
public class SpaceApply implements Serializable {

	private Integer id;
	/**类型,type=1为众创空间，2为空间租赁*/
	private Integer type;
	/**所属孵化器id*/
	private Integer incubatorId;
	/**所属孵化器*/
	private String incubator;
	/**公司名称*/
	private String appellation;
	/**团队人数*/
	private Integer quantity;
	/**成立情况*/
	private String cases;
	/**是否毕业五年以上,1是，2否*/
	private int graduate;
	/**公司经营范围*/
	private String range;
	/**type=1，所需工位数量/type=2，所需办公面积*/
	private Integer area;
	/**联系人*/
	private String name;
	/**联系电话*/
	private String phone;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIncubatorId() {
		return incubatorId;
	}

	public void setIncubatorId(Integer incubatorId) {
		this.incubatorId = incubatorId;
	}

	public String getIncubator() {
		return incubator;
	}

	public void setIncubator(String incubator) {
		this.incubator = incubator;
	}

	public String getAppellation() {
		return appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getCases() {
		return cases;
	}

	public void setCases(String cases) {
		this.cases = cases;
	}

	public int getGraduate() {
		return graduate;
	}

	public void setGraduate(int graduate) {
		this.graduate = graduate;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
