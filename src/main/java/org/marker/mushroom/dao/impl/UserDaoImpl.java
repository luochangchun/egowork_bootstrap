package org.marker.mushroom.dao.impl;

import org.marker.mushroom.beans.User;
import org.marker.mushroom.beans.UserGroup;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IUserDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperUser;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperUserGroup;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl extends DaoEngine implements IUserDao {

	/**
	 * 通过用户名和密码查询用户对象
	 */
	@Override
	public User queryByNameAndPass(String name, String pass) {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append("user").append(" where name=? and pass=?");
		User user = null;
		try {
			user = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] {name, pass}, new RowMapperUser());
		} catch (Exception ignored) {}

		return user;
	}

	@Override
	public boolean updateLoginTime(Serializable id) {
		String prefix = dbConfig.getPrefix();
		int status = jdbcTemplate.update("update " + prefix + "user " + "set logintime=sysdate() where id=?", id);
		return status > 0;
	}

	/* (non-Javadoc)
	 * @see org.marker.mushroom.dao.IUserDao#findUserByName(java.lang.String)
	 */
	@Override
	public User findUserByName(String userName) {
		String prefix = dbConfig.getPrefix();
		StringBuilder sql = new StringBuilder("select * from ");
		sql.append(prefix).append("user").append(" where name=?");
		User user = null;
		try {
			user = this.jdbcTemplate.queryForObject(sql.toString(), new Object[] {userName}, new RowMapperUser());
		} catch (Exception e) {
			logger.error("通过name查询用户失败!", e);
		}

		return user;
	}

	@Override
	public List<UserGroup> findGroup() {
		return this.jdbcTemplate.query("select * from " + getPreFix() + "user_group", new RowMapperUserGroup());
	}

	// 统计用户组的用户数量
	@Override
	public int countUserByGroupId(int groupId) {
		return this.queryForObject("select count(*) from " + getPreFix() + "user u where u.gid=? ", Integer.class, groupId);
	}

}
