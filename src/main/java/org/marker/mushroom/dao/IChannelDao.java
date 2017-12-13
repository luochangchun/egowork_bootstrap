package org.marker.mushroom.dao;

import org.marker.mushroom.beans.Channel;

import java.io.Serializable;
import java.util.List;

/**
 * 栏目查询接口
 *
 * @author marker
 * @version 1.0
 * @date 2013-11-15 下午9:25:24
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public interface IChannelDao extends ISupportDao {

	Channel queryByUrl(String url);

	List<Channel> findAll();

	Channel queryByArticleId(Serializable cid);

	/**
	 * 查询可见栏目数据
	 *
	 * @return
	 */
	List<Channel> findValid();
}
