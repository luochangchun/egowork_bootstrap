package org.marker.mushroom.controller;

import java.util.Date;

import org.marker.mushroom.beans.Apply_service;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.support.SupportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 孵化器申请
 * 
 * @author dd
 */
@Controller
@RequestMapping("/front/service")
public class ServiceFrontController extends SupportController
{

	private static final Logger logger = LoggerFactory.getLogger(ServiceFrontController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public ServiceFrontController()
	{

	}

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(final Apply_service apply_service)
	{
		apply_service.setTime(new Date());
		apply_service.setCreateTime(new Date());

		if (commonDao.save(apply_service))
		{
			return new ResultMessage(true, "提交成功!");
		}
		else
		{
			return new ResultMessage(false, "提交失败!");
		}
	}


}
