package org.marker.mushroom.dao;

import org.marker.mushroom.beans.Module;

import java.util.List;
import java.util.Map;

/**
 * 内容模型
 *
 * @author marker
 * @version 1.0
 */
public interface IModelDao {

	/**
	 * 查询全部的内容模型
	 *
	 * @return List<Module> 集合
	 */
	List<?> queryAll();

	/**
	 * @param modelType
	 */
	boolean deleteByType(String modelType);

	/**
	 * @param modelType
	 * @return
	 */
	Module findByType(String modelType);

	boolean save(Map<String, Object> config);

}
