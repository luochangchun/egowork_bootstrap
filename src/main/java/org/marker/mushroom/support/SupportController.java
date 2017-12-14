package org.marker.mushroom.support;

import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.ICommonDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletContext;

/**
 * 控制器支撑类
 *
 * @author marker
 */
public class SupportController {

	/** 日志记录器 */
	public static Logger log = LoggerFactory.getLogger(SupportController.class);

	@Autowired
	protected JdbcTemplate dao;

	/* 自动注入通用Dao */
	@Autowired
	protected ICommonDao commonDao;

	/*  */
	@Autowired
	protected ServletContext application;

	/**
	 * viewPath为视图的目录
	 */
	protected String viewPath;

	protected static final DataBaseConfig dbConfig = DataBaseConfig.getInstance();
	protected static final String prefix = dbConfig.getPrefix();

}
