package org.marker.mushroom.service.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Article;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service(Services.ARTICLE)
public class ArticleService extends BaseService
{


	@Autowired
	private ISupportDao commonDao;



	public Page find(final int currentPageNo, final int pageSize, final Map<String, Object> condition)
	{
		String keyword = (String) condition.get("keyword");
		final String status = (String) condition.get("status");
		final String cid = (String) condition.get("cid");
		try
		{
			keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
			if (keyword.contains("%"))
			{
				keyword = keyword.replaceAll("%", "!%");
			}
		}
		catch (final UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		final String sql = "select a.*, concat('/cms?type=',c.model,'&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d'))  url, c.name  as cname ,c.model from "
				+ config.getPrefix() + "article as a " + "left join " + config.getPrefix() + "category c on c.id = a.cid "
				+ "where a.status in (" + status + ") and a.cid in (" + cid + ") "
				+ " and a.title like ? ESCAPE '!' order by a.id desc";

		return commonDao.findByPage(currentPageNo, pageSize, sql, '%' + keyword + '%');
	}

	public List<Map<String, Object>> findMapArticleByCid(final int cid)
	{
		final String sql = "select * from " + config.getPrefix() + "article as m1 where 1=1 and m1.status=1 and m1.cid=?";
		return commonDao.queryForList(sql, cid);
	}

	public Article findArticleById(final Serializable id)
	{
		final String sql = "select * from " + config.getPrefix() + "article as m1 where 1=1 and m1.status=1 and m1.id=?";
		return commonDao.queryForObject(sql, Article.class, id);
	}

	public List<Map<String, Object>> findListArticleStatistics(final Integer userId, final Integer groupId, String userName,
			final String beginDate, final String endDate)
	{
		final StringBuilder sql = new StringBuilder();
		sql.append(" select u.name as userName, ").append("\n");
		sql.append(" sum(if(m.status=0,1,0)) as wait_release, ").append("\n");
		sql.append(" sum(if(m.status=1,1,0)) as already_release, ").append("\n");
		sql.append(" sum(if(m.status=2,1,0)) as wait_audit, ").append("\n");
		sql.append(" sum(if(m.status=3,1,0)) as rejected ").append("\n");
		sql.append(" from ");
		sql.append(config.getPrefix());
		sql.append("article m ");
		sql.append(" join ");
		sql.append(config.getPrefix());
		sql.append("user u on m.userId = u.id ");
		sql.append(" where 1=1 ");
		if (groupId != null && groupId != 1)
		{
			sql.append(" and m.userId = ").append(userId);
		}
		if (!StringUtils.isEmpty(userName))
		{
			try
			{
				userName = new String(userName.getBytes("ISO-8859-1"), "utf-8");
			}
			catch (final UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			sql.append(" and u.name like '%" + userName + "%' ");
		}

		if (!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(endDate))
		{
			sql.append(" and DATE_FORMAT(m.createTime,'%Y-%m-%d') BETWEEN '" + beginDate + "' and '" + endDate + "' ");
		}

		sql.append(" group by u.name ");
		return commonDao.queryForList(sql.toString());
	}

	public Map<String, Object> findSumArticle()
	{
		final String sql = "select sum(views) as views from " + config.getPrefix() + "article";
		return commonDao.queryForMap(sql);
	}

}
