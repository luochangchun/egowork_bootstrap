package org.marker.mushroom.controller;

import org.marker.mushroom.beans.InvestProject;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by 30 on 2018/1/3 0003.
 * 融资项目模块
 */
@Controller
@RequestMapping("/admin/investproject")
public class InvestProjectController extends SupportController {

	public InvestProjectController() {
		this.viewPath = "/admin/investproject/";
	}

	/**
	 * 融资列表
	 */
	@RequestMapping("/list")
	public ModelAndView list(Page page, @RequestParam(value = "cid", required = false, defaultValue = "3") int categoryId) {
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		if (categoryId == 3) {
			view.addObject("page",
						   commonDao.findByPage(page.getCurrentPageNo(), 10, "select * from " + prefix + "invest_project"));
		} else {
			view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(), 10,
														"select * from " + prefix + "invest_project where status ="
															+ categoryId));
		}
		view.addObject("categoryId", categoryId);
		return view;
	}

	/**
	 * 修改页面展示
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") long id) {
		ModelAndView view = new ModelAndView(this.viewPath + "edit");
		Map<String, Object> map = commonDao.findById(InvestProject.class, id);
		view.addObject("investproject", map);
		return view;
	}

	/**
	 * 修改保存操作
	 */
	@ResponseBody
	@RequestMapping("/update")
	public ResultMessage update(InvestProject investProject) {
		if (commonDao.update(investProject)) {
			return new ResultMessage(true, "更新成功!");
		} else {
			return new ResultMessage(false, "更新失败!");
		}
	}
	/**
	 * 删除
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public ResultMessage delete(@RequestParam("rid") String rid) {
		Map<String,Object> map = commonDao.findById(InvestProject.class,rid);
		Integer object = (Integer) map.get("status");
		if(object==2){
			if(commonDao.deleteByIds(InvestProject.class,rid)){
				return new ResultMessage(true,"删除成功！");
			}else {
				return new ResultMessage(false,"删除失败！");
			}
		}else {
			return new ResultMessage(false,"非驳回融资项目，无法删除！");
		}
	}
}
