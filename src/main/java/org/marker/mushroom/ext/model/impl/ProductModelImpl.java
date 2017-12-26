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
 * 服务产品模型
 * Created by 30 on 2017/12/19.
 */
public class ProductModelImpl extends ContentModel {

	public ProductModelImpl() {
		final Map<String, Object> config = new HashMap<>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "服务产品模型");
		config.put("author", "dd");
		config.put("version", "0.1");
		config.put("type", "product");
		config.put("template", "product.html");
		config.put("description", "系统内置模型");
		configure(config);
	}

	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable id) {
		final HttpServletRequest request = ActionContext.getReq();

		final String sql =
			"select  M.*,C.name cname," + url("product", "M")
				+ " from " + getPrefix() + "category C right join " + getPrefix() + "product M on M.cid = C.id where M.id=?";
		final Object product = commonDao.queryForMap(sql, id);
		commonDao.update("update " + getPrefix() + "product set views = views+1 where id=?", id);// 更新浏览量
		request.setAttribute("product", product);
	}

	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param) {
		String sql = "select A.*, C.name as cname," + url("product", "A")
			+ " from " + getPrefix() + "product A join " + getPrefix() + "category C on A.cid=C.id"
			+ " where 1=1 " + param.extendSql + param.orderSql;

		return commonDao.findByPage(param.currentPageNo, param.pageSize, sql);
	}

	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 *
	 * @param tableName 表名称
	 */
	public StringBuilder doWebFront(final String tableName, final SqlDataSource sqlDataSource) {
		final String sql = "select M.*, C.name cname," + url("product", "M")
			+ " from " + getPrefix() + "category C right join " + getPrefix() + "product M on M.cid = C.id";
		return new StringBuilder(sql);
	}
}
