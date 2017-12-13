package org.marker.mushroom.dao;

import org.marker.mushroom.alias.LOG;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.annotation.Entity;
import org.marker.mushroom.utils.SQLUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作对象引擎
 * 实现：基于Spring的JDBCTemplate开发的通用对象。
 * 目的：简化数据库代码
 * 最新采用slf4j日志接口
 *
 * @author marker
 * @version 1.0
 * @date 2014-08-10
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public abstract class DaoEngine implements ISupportDao {

	/** 日志记录器 */
	protected Logger logger = LoggerFactory.getLogger(LOG.DAOENGINE);

	/** 数据库配置 */
	protected static final DataBaseConfig dbConfig = DataBaseConfig.getInstance();

	/** 自动注入jdbc模板操作 */
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	/** 基本信息缓存 */
	private static final EntityInfoCache cache = new EntityInfoCache();

	/**
	 * 泛型构造方法目的是为了简化实现
	 */
	public DaoEngine() { }
	
	
	/* 
	 * ==================================================================
	 *                       数据查询接口实现
	 * ==================================================================
	 */

	// 查询单个对象实现
	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) {
		logger(sql);
		return jdbcTemplate.queryForMap(sql, args);
	}

	// 查询对象集合实现
	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		logger(sql);
		return jdbcTemplate.queryForList(sql, args);
	}

	// 查询对象 
	@Override
	public <T> T queryForObject(String sql, Class<T> clzz, Object... args) {
		logger(sql);
		return jdbcTemplate.queryForObject(sql, clzz, args);
	}

 
 
	
	
	/* 
	 * ==================================================================
	 *                       数据更新接口实现
	 * ==================================================================
	 */

	// 批量更新
	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) {
		logger(sql);
		return jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	// 更新数据
	@Override
	public boolean update(String sql, Object... args) {
		logger(sql);
		return jdbcTemplate.update(sql, args) > 0;
	}
	
	
	
	
	
	/* 
	 * ==================================================================
	 *                       数据删除接口实现
	 * ==================================================================
	 */

	// 批量删除
	@Override
	public boolean deleteByIds(Class<?> clzz, String ids) {
		String prefix = dbConfig.getPrefix();// 表前缀

		MapConfig map;
		try {
			map = cache.getMapConfig(clzz);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(prefix).append(map.getTableName())
		   .append(" where ").append(map.getPrimaryKey()).append(" in(")
		   .append(ids).append(")");
		logger(sql.toString());
		return jdbcTemplate.update(sql.toString()) > 0;
	}

	// 批量删除
	@Override
	public boolean deleteByIds(String tableName, String key, String ids) {
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(tableName).append(" where ")
		   .append(key).append(" in(").append(ids).append(")");
		logger(sql.toString());
		return jdbcTemplate.update(sql.toString()) > 0;
	}

	/* =========================================
	 *                 根据ID查询
	 * ========================================= */
	@Override
	public Map<String, Object> findById(Class<?> clzz, Serializable id) {
		String prefix = dbConfig.getPrefix();// 表前缀 
		String tableName = clzz.getAnnotation(Entity.class).value();
		String primaryKey = clzz.getAnnotation(Entity.class).key();
		String sql = "select * from " + prefix + tableName +
			" where " + primaryKey + "=?";
		return queryForMapNoException(sql, id);
	}

	// 无异常查询
	private Map<String, Object> queryForMapNoException(String sql,
													   Object... args) {
		try {
			logger(sql);
			return jdbcTemplate.queryForMap(sql, args);
		} catch (Exception ignored) { }
		return new HashMap<>();
	}

	// 分页查询
	@Override
	public Page findByPage(int currentPageNo, int pageSize, String sql,
						   Object... args) {

		int beginPos = sql.toLowerCase().indexOf("from");
		String hql4Count = "select count(*) " + sql.substring(beginPos);
		sql += " limit " + (currentPageNo - 1) * pageSize + "," + pageSize;

		// 获取总条数
		int totalRows = jdbcTemplate.queryForObject(hql4Count, Integer.class, args);

		List<Map<String, Object>> data = queryForList(sql, args);

		Page page = new Page(currentPageNo, totalRows, pageSize);
		page.setData(data);// 获取数据集合 
		return page;
	}

	// 保存对象  
	public boolean save(Object entity) {
		String prefix = dbConfig.getPrefix();
		Class<?> clzz = entity.getClass();

		MapConfig map;
		try {
			map = cache.getMapConfig(clzz);
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}

		// 构造SQL字符串
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(prefix).append(map.getTableName()).append("(");
		StringBuilder val = new StringBuilder(" values(");
		Field[] fields = clzz.getDeclaredFields();
		int length = fields.length;
		final List<Object> list = new ArrayList<>(length);
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.equals(map.getPrimaryKey()) || "serialVersionUID".equals(fieldName)) continue;

			Object returnObject;
			try {
				String methodName = "get" + fieldName.replaceFirst(fieldName.charAt(0) + "",
																   (char) (fieldName.charAt(0) - 32) + "");
				Method me = clzz.getMethod(methodName);
				returnObject = me.invoke(entity);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			sql.append("`").append(fieldName).append("`,");
			val.append("?,");
			list.add(returnObject);
		}

		final StringBuilder sql2 = new StringBuilder(sql.substring(0, sql.length() - 1));
		StringBuilder val2 = new StringBuilder(val.substring(0, val.length() - 1));

		sql2.append(")").append(val2).append(")");
		KeyHolder holder = new GeneratedKeyHolder();

		this.jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql2.toString(), Statement.RETURN_GENERATED_KEYS);
			Object[] params = list.toArray();
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps;
		}, holder);

		try {
			Method me = clzz.getMethod("setId", Integer.class);
			me.invoke(entity, holder.getKey().intValue());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// 更新数据
	public boolean update(Object entity) {
		String prefix = dbConfig.getPrefix();
		Class<?> clzz = entity.getClass();
		Entity tableInfo = clzz.getAnnotation(Entity.class);
		String tableName = tableInfo.value();
		String primaryKey = tableInfo.key();

		StringBuffer sql = new StringBuffer();
		sql.append("update `").append(prefix).append(tableName)
		   .append("` set ");
		List<Object> list = new ArrayList<>();
		int id = 0;
		try {
			id = (Integer) clzz.getMethod("getId").invoke(entity);
			Field[] fields = clzz.getDeclaredFields();
			int length = fields.length;
			list = new ArrayList<>(length);
			for (Field field : fields) {
				String fieldName = field.getName();
				if (fieldName.equals(primaryKey) || "serialVersionUID".equals(fieldName)) continue;

				String methodName = "get" + fieldName.replaceFirst(
					fieldName.charAt(0) + "", (char) (fieldName.charAt(0) - 32) + "");
				Method me = clzz.getMethod(methodName);
				Object returnObject = me.invoke(entity);
				if (returnObject != null) {// 如果返回值为null
					sql.append("`").append(fieldName).append("`").append("=?");
					list.add(returnObject);
					sql.append(",");
				}
			}
			sql = new StringBuffer(sql.substring(0, sql.length() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		list.add(id);
		sql.append(" where `").append(primaryKey).append("`=?");

		return update(sql.toString(), list.toArray());
	}

	// 查询某页数据
	@Override
	public List<Map<String, Object>> queryFotList(int currentPageNo,
												  int pageSize,
												  String sql,
												  Object... args) {
		sql += " limit " + (currentPageNo - 1) * pageSize + "," + pageSize;
		logger(sql);
		return jdbcTemplate.queryForList(sql, args);
	}

	/**
	 * 获取数据库前缀
	 *
	 * @return
	 */
	protected String getPreFix() {
		return dbConfig.getPrefix();
	}

	/**
	 * 日志打印方法
	 * 如果是数据库开发模式，方可打印日志
	 *
	 * @param info
	 */
	protected void logger(String info, Object... params) {
		if (dbConfig.isDebug()) { // debug模式输入SQL语句
			info = SQLUtil.format(info);
			logger.debug(info, params);
		}
	}

}
