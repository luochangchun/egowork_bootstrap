package org.marker.mushroom.controller;

import org.marker.mushroom.beans.OnlineApply;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 30 on 2017/12/18 0018.
 * 线上孵化器申请
 */
@Controller
@RequestMapping("/admin/onlineapply")
public class OnlineApplyController extends SupportController {

	public OnlineApplyController() {
		this.viewPath = "/admin/onlineapply/";
	}

	/**
	 * 页面展示
	 *
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page) {
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 10,
													"select * from " + prefix + "online_apply order by id desc"));
		return view;
	}

	/**
	 * 删除
	 *
	 * @param rid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ResultMessage delect(@RequestParam("rid") String rid) {
		Boolean struts = commonDao.deleteByIds(OnlineApply.class, rid);
		if (struts) {
			return new ResultMessage(true, "删除成功！");
		} else {
			return new ResultMessage(false, "删除失败！");
		}
	}

}
