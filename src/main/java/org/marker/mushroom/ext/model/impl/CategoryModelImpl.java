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
 * Created by 30 on 2017/12/26.
 */
public class CategoryModelImpl extends ContentModel {

	public CategoryModelImpl() {
		final Map<String, Object> config = new HashMap<>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "分类模型");
		config.put("author", "30");
		config.put("version", "0.1");
		config.put("type", "category");
		config.put("template", "category.html");
		config.put("description", "系统定制模型");
		configure(config);
	}

	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable id) {
		final String prefix = getPrefix();//表前缀，如："yl_"
		final HttpServletRequest request = ActionContext.getReq();

		final String sql = "select M.*,"
			+ " concat('/cms?','type=cateogry','&id=',CAST(M.id as char)) url"
			+ " from " + prefix + "category M where M.id=?";
		final Object category = commonDao.queryForMap(sql, id);
		request.setAttribute("category", category);
		request.setAttribute("products", commonDao.queryForList("select * from mr_product where cid = " + id));
	}

	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param) {
		final String sql = "select M.*,"
			+ " concat('/cms?','type=cateogry','&id=',CAST(M.id as char)) url"
			+ " from " + getPrefix() + "category M where 1=1 " + param.extendSql + param.orderSql;
		return commonDao.findByPage(param.currentPageNo, param.pageSize, sql);
	}

	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 *
	 * @param tableName 表名称
	 */
	public StringBuilder doWebFront(final String tableName, final SqlDataSource sqlDataSource) {
		return new StringBuilder(
			"select M.*, concat('/cms?','type=cateogry','&id=',CAST(M.id as char)) url from " + getPrefix() + "category M");
	}
}
