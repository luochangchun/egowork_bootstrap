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
 * 文章模型实现
 *
 * @author marker
 * @version 1.0
 */
public class IncubatorModelImpl extends ContentModel {

	public IncubatorModelImpl() {
		final Map<String, Object> config = new HashMap<>();
		config.put("icon", "images/demo.jpg");
		config.put("name", "孵化器模型");
		config.put("author", "dd");
		config.put("version", "0.1");
		config.put("type", "incubator");
		config.put("template", "incubator.html");
		config.put("description", "系统内置模型");
		configure(config);
	}

	/**
	 * 抓取内容
	 */
	public void fetchContent(final Serializable id) {
		final String prefix = getPrefix();//表前缀，如："yl_"
		final HttpServletRequest request = ActionContext.getReq();

		final String sql =
			"select  M.*,C.name cname, concat('/cms?','type=incubator','&id=',CAST(M.id as char),'&time=',DATE_FORMAT(M.time,"
				+ "'%Y%m%d')) url from "
				+ prefix + "category C " + "right join " + prefix + "incubator M on M.cid = C.id  where  M.id=?";
		final Object incubator = commonDao.queryForMap(sql, id);
		commonDao.update("update " + prefix + "incubator set views = views+1 where id=?", id);// 更新浏览量

		final String photoSql = "select id, uri from " + getPrefix() + "incubator_photo where incubatorId = ?";

		request.getSession().setAttribute("incubator", incubator);
		// 必须发送数据
		request.setAttribute("photos", commonDao.queryForList(photoSql, id));
		request.setAttribute("incubator", incubator);
	}

	/**
	 * 处理分页
	 */
	public Page doPage(final WebParam param) {
		final String prefix = getPrefix();//表前缀，如："yl_" 

		String sql = "select A.*, C.name as cname,"
			+ " concat('/cms?', 'type=incubator&id=',CAST(A.id as char),'&time=',DATE_FORMAT(A.time,'%Y%m%d')) as url"
			+ " from " + prefix + "incubator A join " + prefix + "category C on A.cid=C.id"
			+ " where 1=1 " + param.extendSql + param.orderSql;

		return commonDao.findByPage(param.currentPageNo, param.pageSize, sql);
	}

	/**
	 * 前台标签生成SQL遇到该模型则调用模型内算法
	 *
	 * @param tableName 表名称
	 */
	public StringBuilder doWebFront(final String tableName, final SqlDataSource sqlDataSource) {
		final String prefix = getPrefix();// 表前缀，如："yl_"

		final StringBuilder sql =  new StringBuilder();
		sql.append("select M.*, C.name cname,")
		   .append(" concat('/cms?','type=incubator','&id=',CAST(M.id as char),'&time=',DATE_FORMAT(M.time,'%Y%m%d')) url")
		   .append(" from ").append(prefix).append("category C")
		   .append(" right join ").append(prefix).append("incubator M on M.cid = C.id");
		return sql;
	}

}
