package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.TrainingApply;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 30 on 2017/12/19 0019.
 * 培训需求申请后台
 */
@Controller
@RequestMapping("/admin/trainingapply")
public class TrainingApplyController extends SupportController {

	public TrainingApplyController() {
		this.viewPath = "/admin/trainingapply/";
	}

	/**
	 * 页面展示
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page) {
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 10,
													"select * from " + prefix + "training_apply order by id desc"));
		return view;
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage delete(@RequestParam("rid")String rid){
		Boolean bool = commonDao.deleteByIds(TrainingApply.class,rid);
		if(bool){
			return new ResultMessage(true,"删除成功！");
		}else{
			return new ResultMessage(false,"删除失败！");
		}
	}
}
