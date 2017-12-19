package org.marker.mushroom.controller;

import org.marker.mushroom.beans.NeedsApply;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 30 on 2017/12/19 0019.
 * 服务需求申请展示
 */
@Controller
@RequestMapping("/admin/needsapply")
public class NeedsApplyController extends SupportController{

	public NeedsApplyController () {
		this.viewPath = "/admin/needsapply/";
	}
	/**
	 * 展示页面
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page){
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page",commonDao.findByPage(page.getCurrentPageNo(),10,"select * from "+prefix+"needs_apply order by id desc"));
		return view;
	}
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage delete(@RequestParam("rid")String rid){
		Boolean bool = commonDao.deleteByIds(NeedsApply.class,rid);
		if(bool){
			return new ResultMessage(true,"删除成功！");
		}else{
			return new ResultMessage(false,"删除失败！");
		}
	}
	/**
	 * 详情页面
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id")String id){
		ModelAndView view = new ModelAndView(this.viewPath + "detail");
		view.addObject("needsapply",commonDao.findById(NeedsApply.class,id));
		return view;
	}
}
