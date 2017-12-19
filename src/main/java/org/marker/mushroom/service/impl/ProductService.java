package org.marker.mushroom.service.impl;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 30 on 2017/12/19.
 */
@Service
public class ProductService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	public Page findByPage(int pageNo, int pageSize, int cid) {
		String sql = "select * from " + config.getPrefix() + "product";
		if (cid > 0) sql += " where cid = " + cid;
		sql += " order by id desc";
		return commonDao.findByPage(pageNo, pageSize, sql);
	}
}
