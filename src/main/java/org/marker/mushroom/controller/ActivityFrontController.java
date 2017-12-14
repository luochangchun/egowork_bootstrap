package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ApplyActivity;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 我要报名
 *
 * @author dd
 */
@Controller
@RequestMapping("/front/activity")
public class ActivityFrontController extends SupportController {

	public ActivityFrontController() { }

	@PostMapping("/add")
	@ResponseBody
	public Object add(final ApplyActivity applyActivity) {
		applyActivity.setTime(new Date());
		applyActivity.setCreateTime(new Date());

		if (commonDao.save(applyActivity)) {
			return new ResultMessage(true, "提交成功!");
		} else {
			return new ResultMessage(false, "提交失败!");
		}
	}

}
