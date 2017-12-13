package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Permission;
import org.marker.mushroom.beans.UserObject;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IPermissionDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperPermission;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperUserObject;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author marker
 */
@Repository(DAO.PERMISSION)
public class PermissionDaoImpl extends DaoEngine implements IPermissionDao {

	// 如果分组ID查询组内权限
	public List<Permission> findPermissionByGroupId(final int groupId) {
		return this.jdbcTemplate.query("select gid,mid from " + getPreFix() + "user_group_menu where gid=?",
									   new Object[] {groupId},
									   new RowMapperPermission());

	}

	@Override
	public List<UserObject> findUserObjectByGroupId(final int groupId, final String type) {
		return this.jdbcTemplate.query("select gid,oid,type from " + getPreFix() + "user_group_object where gid=? and type= ? ",
									   new Object[] {groupId, type},
									   new RowMapperUserObject());
	}

}
