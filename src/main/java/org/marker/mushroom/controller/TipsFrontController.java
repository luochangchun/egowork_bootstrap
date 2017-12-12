package org.marker.mushroom.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.marker.mushroom.beans.Tips;
import org.marker.mushroom.service.impl.TipsService;
import org.marker.mushroom.support.SupportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * 补贴券管理
 * 
 * @author dd
 * 
 */
@Controller
@RequestMapping("/front/tips")
public class TipsFrontController extends SupportController
{

	/** 分类业务对象 */
	@Autowired
	private TipsService tipsService;

	// 显示有补贴券的公司
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map list()
	{
		final Map<String, Object> view = new HashMap<>();
		try
		{
			final List<Map<String, Object>> tips = tipsService.find();
			view.put("tips", tips);
			view.put("code", 200);
		}
		catch (final Exception e)
		{
			view.put("code", 500);
			view.put("error", e.getMessage());
		}

		return view;
	}

	// 领取补贴券
	@ResponseBody
	@RequestMapping(value = "/claim", method = RequestMethod.POST)
	public Map claim(final Tips tips)
	{
		final Map<String, Object> view = new HashMap<>();

		final Map<String, Object> isTips = tipsService.find(tips.getHrId(), tips.getCid(), tips.getUserId());

		final Long con = isTips.get("con") == null ? 0 : (Long) isTips.get("con");
		if (con == 0)
		{
			try
			{
				tips.setUserName(new String(tips.getUserName().getBytes("ISO-8859-1"), "utf-8"));
			}
			catch (final UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			tips.setTime(new Date());

			if (commonDao.save(tips))
			{
				view.put("message", "领取成功！");
			}
			else
			{
				view.put("message", "领取失败！");
			}
		}
		else
		{
			view.put("message", "此账户已经领取此服务商补贴券，一个账户至多只能领取一次！");
		}

		return view;
	}

}
