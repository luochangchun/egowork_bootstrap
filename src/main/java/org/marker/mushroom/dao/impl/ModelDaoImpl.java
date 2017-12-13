package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Module;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IModelDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository(DAO.MODEL)
public class ModelDaoImpl extends DaoEngine implements IModelDao {

	@Override
	public List<?> queryAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(getPreFix()).append("model ");
		logger(sql.toString());
		return this.jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapperModel());
	}

	@Override
	public boolean deleteByType(String modelType) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(getPreFix()).append("model")
		   .append(" where ").append("type=?");
		logger(sql.toString());
		return jdbcTemplate.update(sql.toString(), modelType) > 0;
	}

	@Override
	public Module findByType(String moduleType) {
		return null;
	}

	@Override
	public boolean save(Map<String, Object> config) {
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(getPreFix()).append("model")
		   .append(" values(null,?,?,?,?,?,?,sysdate(),?)");
		logger(sql.toString());
		int status = jdbcTemplate.update(sql.toString(),
										 config.get("name"),
										 config.get("icon"),
										 config.get("version"),
										 config.get("template"),
										 config.get("type"),
										 config.get("author"),
										 config.get("module")
		);

		return status > 0;

	}

}
