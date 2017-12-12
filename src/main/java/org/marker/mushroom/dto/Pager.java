package org.marker.mushroom.dto;

import java.io.Serializable;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Pager implements Serializable{

	private static final long serialVersionUID = 8922931101628971224L;

	public static final Integer MAX_PAGE_SIZE = Integer.valueOf(500);

	/*当前页*/
	private Integer pageNumber = Integer.valueOf(1);

	/*每页记录数*/
	private Integer pageSize = Integer.valueOf(20);

	/*总记录数*/
	private Integer totalCount = Integer.valueOf(0);

	/*总页数*/
	private Integer pageCount = Integer.valueOf(0);

	/*查询字段*/
	private String property;

	/*查询字段值*/
	private String keyword;

	/*查询排序字段*/
	private String orderBy = "";
	
	/* 查询字段 (对应bean属性):值      支持多个条件*/
	private HashMap queryMap;

	/*排序类型*/
	private OrderType orderType = OrderType.desc;

	/*结果集*/
	private Object result;
	
	/*提示信息*/
	private String message;

	/**
	 * 返回码
	 *	200:成功
     *  500:后台校验失败    501:第三方接口返回失败    503:第三方接口调用失败    504:权限校验失败
     *  400:报错 
	 */
	private int code;
	
	//是否包含全部数据，做假分页
	private boolean isFull = false;

	public Integer getPageNumber() {
		return this.pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber.intValue() < 1) {
			pageNumber = Integer.valueOf(1);
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize.intValue() < 1)
			pageSize = Integer.valueOf(1);
		else if (pageSize.intValue() > MAX_PAGE_SIZE.intValue()) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		this.pageCount = Integer.valueOf(this.totalCount.intValue()
				/ this.pageSize.intValue());
		if (this.totalCount.intValue() % this.pageSize.intValue() > 0) {
			Pager tmp41_40 = this;
			tmp41_40.pageCount = Integer
					.valueOf(tmp41_40.pageCount.intValue() + 1);
		}
		return this.pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public OrderType getOrderType() {
		return this.orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public static enum OrderType {
		asc, desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public HashMap getQueryMap() {
		return queryMap;
	}

	public void setQueryMap(HashMap queryMap) {
		this.queryMap = queryMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean full) {
		isFull = full;
	}
	
}
