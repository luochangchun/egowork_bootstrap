package org.marker.mushroom.ext.tag.impl;

import org.marker.mushroom.ext.tag.Taglib;

import java.util.HashMap;
import java.util.Map;

/**
 * URL绝对路径重写标签 作用于主题目录：images、css、js、static文件夹 格式:
 *
 * @author marker
 * @version 1.0
 * @date 2013-8-24 下午12:38:13
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class AbsoluteURLTagImpl extends Taglib {

	/** 默认构造 */
	public AbsoluteURLTagImpl() {
		final Map<String, Object> config = new HashMap<>();
		config.put("name", "绝对路径处理");
		config.put("author", "marker");
		config.put("doc", "doc/1.md");
		config.put("description", "系统内置");
		this.configure(config);

		this.put("(src=|href=|background=)[\"\']((?!http://)(?!/)(?!\\$)(?!\\#)(?!https://)(?!/)(?!\\$)(?!\\#).+)[\"\']",
				 "$1\"\\${url}/themes/\\${config.themes_path}/$2\"", 0);
	}

}
