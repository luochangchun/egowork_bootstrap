package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Article;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IArticleDao;
import org.springframework.stereotype.Repository;




@Repository(DAO.ARTICLE)
public class ArticleDaoImpl extends DaoEngine implements IArticleDao
{

	@Override
	public boolean update(final Article a)
	{
		final String prefix = dbConfig.getPrefix();//获取数据库表前缀

		final StringBuilder sql = new StringBuilder();
		sql.append("update ").append(prefix).append("article").append(
				" set cid=?, title=?,keywords=?,description=?,author=?, content=?,status=?,source=?,icon=?,time=?,reject=? where id=?");

		final int status = jdbcTemplate.update(sql.toString(), a.getCid(), a.getTitle(), a.getKeywords(), a.getDescription(),
				a.getAuthor(), a.getContent(), a.getStatus(), a.getSource(), a.getIcon(), a.getTime(), a.getReject(), a.getId());

		return status > 0 ? true : false;
	}




}
