package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ApplyService;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 孵化器申请
 *
 * @author dd
 */
@Controller
@RequestMapping("/front/service")
public class ServiceFrontController extends SupportController {

	public ServiceFrontController() {
	}

	@PostMapping("/add")
	@ResponseBody
	public Object add(final ApplyService applyService) {
		applyService.setTime(new Date());
		applyService.setCreateTime(new Date());

		if (commonDao.save(applyService)) {
			return new ResultMessage(true, "提交成功!");
		} else {
			return new ResultMessage(false, "提交失败!");
		}
	}

}
