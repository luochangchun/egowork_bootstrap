package org.marker.mushroom.service.impl;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service(Services.TIPS)
public class TipsService extends BaseService
{


	@Autowired
	private ISupportDao commonDao;



	public List<Map<String, Object>> find()
	{
		final String sql = "select h.id,h.title,h.cid,c.name from " + config.getPrefix()
				+ "human_resources h join mr_category c on h.cid=c.id where h.tips=1";

		return commonDao.queryForList(sql);
	}

	public Page find(final int currentPageNo, final int pageSize)
	{
		final String sql = "select t.userName,t.phone,t.time,hr.title provider,c.name category from " + config.getPrefix()
				+ "tips t," + config.getPrefix() + "human_resources hr," + config.getPrefix()
				+ "category c where t.hrId=hr.id and t.cid=c.id";

		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

	public Map<String, Object> find(final Integer hrId, final Integer cid, final Integer userId)
	{
		final String sql = "select count(1) con from " + config.getPrefix()
				+ "tips t where t.hrId = ? and t.cid = ? and t.userId = ?";

		return commonDao.queryForMap(sql, hrId, cid, userId);
	}

}
