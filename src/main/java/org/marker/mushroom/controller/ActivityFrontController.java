package org.marker.mushroom.controller;

import java.util.Date;

import org.marker.mushroom.beans.Apply_activity;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.support.SupportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 我要报名
 * 
 * @author dd
 */
@Controller
@RequestMapping("/front/activity")
public class ActivityFrontController extends SupportController
{

	@Autowired
	CategoryService categoryService;

	private static final Logger logger = LoggerFactory.getLogger(ActivityFrontController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public ActivityFrontController()
	{

	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(final Apply_activity apply_activity)
	{
		apply_activity.setTime(new Date());
		apply_activity.setCreateTime(new Date());

		if (commonDao.save(apply_activity))
		{
			return new ResultMessage(true, "提交成功!");
		}
		else
		{
			return new ResultMessage(false, "提交失败!");
		}
	}


}
