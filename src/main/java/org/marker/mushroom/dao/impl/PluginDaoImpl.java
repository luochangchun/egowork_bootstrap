package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Plugin;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IPluginDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperPlugin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 插件数据库操作对象
 *
 * @author marker
 */
@Repository(DAO.PLUGIN)
public class PluginDaoImpl extends DaoEngine implements IPluginDao {

	/**
	 * 查询所有插件
	 */
	@Override
	public List<Plugin> queryAll() {
		return jdbcTemplate.query("select * from " + getPreFix() + "plugin" + " where status=1", new RowMapperPlugin());
	}

	@Override
	public Plugin findByMark(String mark) {
		String prefix = dbConfig.getPrefix();
		return jdbcTemplate.queryForObject("select id,name,uri,mark,status from " + prefix + "plugin" + " where mark=?",
										   new Object[] {mark},
										   new RowMapperPlugin());
	}

	@Override
	public boolean check(String uuid) {
		String prefix = dbConfig.getPrefix();
		return jdbcTemplate.queryForObject("select count(id) from " + prefix + "plugin" + " where uuid=?",
										   Boolean.class,
										   uuid);
	}

}
