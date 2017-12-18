package org.marker.mushroom.ext.model.impl;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.core.WebParam;
import org.marker.mushroom.ext.model.ContentModel;
import org.marker.mushroom.template.tags.res.SqlDataSource;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 孵化项目模型
 * Created by 30 on 2017/12/18.
 */
public class SeedModelImpl extends ContentModel {

	public SeedModelImpl() {
		final Map<String, Object> config = new HashMap<>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "孵化项目模型");
		config.put("author", "30");
		config.put("version", "0.1");
		config.put("type", "seed");
		config.put("template", "seed.html");
		config.put("description", "系统定制模型");
		configure(config);
	}

	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable id) {
		final String prefix = getPrefix();//表前缀，如："yl_"
		final HttpServletRequest request = ActionContext.getReq();

		final String sql = "select M.*," + url("seed", "M") + " from " + prefix + "seed M where M.id=?";
		final Object enterprise = commonDao.queryForMap(sql, id);
		request.setAttribute("enterprise", enterprise);
	}

	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param) {
		final String sql = "select M.*," + url("seed", "M") + " from "
			+ getPrefix() + "seed M where 1=1 " + param.extendSql + param.orderSql;
		return commonDao.findByPage(param.currentPageNo, param.pageSize, sql);
	}

	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 *
	 * @param tableName 表名称
	 */
	public StringBuilder doWebFront(final String tableName, final SqlDataSource sqlDataSource) {
		return new StringBuilder("select M.*," + url("seed", "M") + " from " + getPrefix() + "financing M");
	}
}
