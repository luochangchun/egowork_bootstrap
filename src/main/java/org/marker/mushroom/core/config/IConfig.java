package org.marker.mushroom.core.config;

import java.util.Properties;

/**
 * 配置引擎接口
 *
 * @author marker
 * @version 1.0
 * @date 2013-11-15 下午5:09:36
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public interface IConfig {

	/**
	 * 获取值
	 *
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 设置键值
	 *
	 * @param key
	 * @param value
	 */
	void set(String key, String value);

	/**
	 * 获取内部Properties
	 *
	 * @return
	 */
	Properties getProperties();

	/**
	 * 持久化配置文件
	 */
	void store();

}
