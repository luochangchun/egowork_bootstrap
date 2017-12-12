package org.marker.mushroom.service.impl;

import java.util.List;
import java.util.Map;

import org.marker.mushroom.alias.Services;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(Services.DICTIONARIES)
public class DictionariesService extends BaseService
{


	@Autowired
	private ISupportDao commonDao;



	public List<Map<String, Object>> findDictionaries(final String name, final String type)
	{
		final String sql = "select * from " + config.getPrefix() + "dictionaries as m1 where 1=1 and m1.name = ? and m1.type = ? ";

		return commonDao.queryForList(sql, name, type);
	}

}
