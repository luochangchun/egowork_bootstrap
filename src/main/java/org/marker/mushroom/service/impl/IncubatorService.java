package org.marker.mushroom.service.impl;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
public class IncubatorService extends BaseService {

	@Autowired
	private ISupportDao commonDao;

	public Page find(final int currentPageNo, final int pageSize, final Map<String, Object> condition) {
		String keyword = (String) condition.get("keyword");
		final String status = (String) condition.get("status");
		final String cid = (String) condition.get("cid");
		final Integer userId = (Integer) condition.get("userid");
		//		final Integer groupid = (Integer) condition.get("groupid");
		//		final String audit = (String) condition.get("audit");
		try {
			keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
			if (keyword.contains("%")) keyword = keyword.replaceAll("%", "!%");
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		final String sql =
			"select a.*, concat('/cms?type=',c.model,'&id=',CAST(a.id as char),'&time=',DATE_FORMAT(a.time,'%Y%m%d')) url,"
				+ " c.name as cname, c.model from " + config.getPrefix() + "incubator as a"
				+ " left join " + config.getPrefix() + "category c on c.id = a.cid"
				+ " where a.status in (" + status + ") and a.cid in (" + cid + ")"
				+ " and a.title like ? ESCAPE '!' order by a.id desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql, '%' + keyword + '%');
	}

	public Page find(final int currentPageNo, final int pageSize) {
		final String sql = " select ai.* from " + config.getPrefix() + "apply_incubator ai order by ai.time desc";
		return commonDao.findByPage(currentPageNo, pageSize, sql);
	}

}
