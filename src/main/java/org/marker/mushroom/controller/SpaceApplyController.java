package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.SpaceApply;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 30 on 2017/12/18 0018.
 * 空间工位租赁和众创空间出租
 */
@Controller
@RequestMapping("/admin/spaceapply")
public class SpaceApplyController extends SupportController {

	public SpaceApplyController() {
		this.viewPath = "/admin/spaceapply/";
	}

	//页面展示
	@RequestMapping("/list")
	public ModelAndView list(Page page, @RequestParam(value = "cid", required = false, defaultValue = "0") int ApplyId) {
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		if (ApplyId != 2 && ApplyId != 1) {
			view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 10,
														"select * from " + prefix + "space_apply order by id desc"));
		} else {
			view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 10,
														"select * from " + prefix + "space_apply where type = " + ApplyId));
		}
		view.addObject("ApplyId", ApplyId);
		return view;
	}

	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage delete(@RequestParam("rid") String rid) {
		Boolean bool = commonDao.deleteByIds(SpaceApply.class, rid);
		if (bool) {
			return new ResultMessage(true, "删除成功！");
		} else {
			return new ResultMessage(false, "删除失败！");
		}
	}
}
