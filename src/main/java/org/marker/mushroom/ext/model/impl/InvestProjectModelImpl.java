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
 * Created by 30 on 2018/1/4.
 */
public class InvestProjectModelImpl extends ContentModel {

	public InvestProjectModelImpl() {
		final Map<String, Object> config = new HashMap<>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "融资项目模型");
		config.put("author", "dd");
		config.put("version", "0.1");
		config.put("type", "investProject");
		config.put("template", "investProject.html");
		config.put("description", "系统定制模型");
		configure(config);
	}

	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable id) {
		final String prefix = getPrefix();//表前缀，如："yl_"
		final HttpServletRequest request = ActionContext.getReq();

		final String sql =
			"select  M.*,C.name cname, concat('/cms?','type=investProject','&id=',CAST(M.id as char),'&time=',DATE_FORMAT(M.time,"
				+ "'%Y%m%d')) url from " + prefix + "invest_project M where M.id=?";
		final Object investProject = commonDao.queryForMap(sql, id);

		// 必须发送数据
		request.setAttribute("investProject", investProject);
	}

	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param) {
		final String sql = "select A.*," + url("investProject", "A") + " from "
			+ getPrefix() + "invest_project A where 1=1 " + param.extendSql + param.orderSql;
		return commonDao.findByPage(param.currentPageNo, param.pageSize, sql);
	}

	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 *
	 * @param tableName 表名称
	 */
	public StringBuilder doWebFront(final String tableName, final SqlDataSource sqlDataSource) {
		return new StringBuilder("select M.*," + url("investProject", "M") + " from " + getPrefix() + "invest_project M");
	}
}
