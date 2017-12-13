package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Category;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.ICategoryDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类Dao
 *
 * @author marker
 * @version 1.0
 */
@Repository(DAO.CATEGORY)
public class CategoryDaoImpl extends DaoEngine implements ICategoryDao {

	public List<Category> list() {
		String sql = "select c.*, m.name modelName from " + getPreFix() +
			"category c " + " left join " + getPreFix() +
			"model m on c.model=m.type order by c.sort asc";
		return this.jdbcTemplate.query(sql, new RowMapperCategory());
	}

}
