/**
 * 吴伟 版权所有
 */
package org.marker.mushroom.dao.impl;

import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Menu;
import org.marker.mushroom.dao.DaoEngine;
import org.marker.mushroom.dao.IMenuDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperMenu;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author marker
 * @version 1.0
 * @date 2013-10-6 上午11:36:12
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
@Repository(DAO.MENU)
public class MenuDaoImpl extends DaoEngine implements IMenuDao {

	// 判断是否有子节点
	public boolean hasChildMenu(int menuId) {
		Integer rows = this.jdbcTemplate.queryForObject(
			"select count(id) from " + dbConfig.getPrefix() + "user_menu where pid = ?", new Object[] {menuId}, Integer.class);
		return rows > 0;
	}

	// 查询一级菜单
	public List<Menu> findTopMenu() {
		return this.jdbcTemplate.query("select * from " + dbConfig.getPrefix() + "user_menu where pid = 0 order by sort",
									   new RowMapperMenu());
	}

	// 根据ID查询子菜单
	public List<Menu> findChildMenuById(int id) {
		return this.jdbcTemplate.query("select * from " + dbConfig.getPrefix() + "user_menu where pid = ?  order by sort",
									   new Object[] {id}, new RowMapperMenu());
	}

	// 根据ID查询菜单
	public Menu findMenuById(int id) {
		return this.jdbcTemplate.queryForObject("select * from " + dbConfig.getPrefix() + "user_menu where id = ?",
												new Object[] {id}, new RowMapperMenu());
	}

	// 根据分组ID查询菜单
	public List<Menu> findTopMenuByGroupId(Serializable groupId) {
		String sql = "select m.* from " + dbConfig.getPrefix() + "user_menu m JOIN " +
			getPreFix() + "user_group_menu gm on gm.mid =m.id where gm.gid = ? and m.pid = 0 order by m.sort";
		return this.jdbcTemplate.query(sql, new Object[] {groupId}, new RowMapperMenu());
	}

	// 根据父级ID和分组ID查询子菜单
	public List<Menu> findChildMenuByGroupAndParentId(Serializable groupId,
													  Serializable parentId) {
		String sql = "select m.* from " + dbConfig.getPrefix() + "user_menu m JOIN " +
			getPreFix() + "user_group_menu gm on gm.mid =m.id where gm.gid = ? and m.pid = ? order by m.sort";
		return this.jdbcTemplate.query(sql, new Object[] {groupId, parentId}, new RowMapperMenu());
	}

	// 根据类型名称查询菜单ID
	public int findMenuIdByType(String type) {
		return super.queryForObject("select id from " + getPreFix() + "user_menu where type=?", Integer.class, type);
	}

	@Override
	public void saveMenuToAdminGroup(Serializable menuId) {
		super.update("insert into " + getPreFix() + "user_group_menu(gid,mid) values(1,?)", menuId);
	}

	@Override
	public void deleteGroupMenu(Serializable menuId) {
		super.update("delete from " + getPreFix() + "user_group_menu where mid=?", menuId);
	}

	@Override
	public Menu findByName(String name) {
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(dbConfig.getPrefix()).append("user_menu where name=?");
		try {
			return this.jdbcTemplate.queryForObject(sql.toString(), new RowMapperMenu(), name);
		} catch (Exception ignored) {}
		return null;
	}

	// 检查菜单是否存在
	public boolean checkType(String type) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(id) from ").append(dbConfig.getPrefix()).append("user_menu where type=?");
		try {
			return this.jdbcTemplate.queryForObject(sql.toString(), Boolean.class, type);
		} catch (Exception ignored) {}
		return false;
	}

}
