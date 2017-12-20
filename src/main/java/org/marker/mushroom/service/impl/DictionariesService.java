package org.marker.mushroom.service.impl;

import org.marker.mushroom.beans.Dictionaries;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.dao.mapper.ObjectRowMapper.RowMapperDict;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionariesService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	public List<Dictionaries> findDictionaries(final String name, final String type) {
		final String sql = "select * from " + config.getPrefix() + "dictionaries as m1 where m1.name = ? and m1.type = ?";
		return dao.query(sql, new RowMapperDict(), name, type);
	}

	public List<Dictionaries> findDictionaries(final String type) {
		final String sql = "select * from " + config.getPrefix() + "dictionaries as m1 where m1.type = ?";
		return dao.query(sql, new RowMapperDict(), type);
	}

}
