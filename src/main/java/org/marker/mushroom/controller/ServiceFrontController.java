package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ApplyService;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

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

	/**
	 * 获取服务产品
	 * @param cid 产品分类id
	 * @return
	 */
	@GetMapping("/{cid}")
	@ResponseBody
	public List get(@PathVariable("cid") int cid) {
		String sql = "select * from mr_product where cid = " + cid;
		return commonDao.queryForList(sql);
	}
}
