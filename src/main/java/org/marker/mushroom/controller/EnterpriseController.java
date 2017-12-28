package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Enterprise;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by 30 on 2017/12/15 0015.
 * 入孵企业管理
 */
@Controller
@RequestMapping("/admin/enterprise")
public class EnterpriseController extends SupportController {


	public EnterpriseController(){
		this.viewPath = "/admin/enterprise/";
	}

	//显示入孵企业页面
	@RequestMapping("/list")
	public ModelAndView list(Page page){
		ModelAndView view = new ModelAndView(this.viewPath+"list");
		view.addObject("page", commonDao.findByPage(page.getCurrentPageNo(),10,"select * from "+prefix+"enterprise order by id desc"));
		return view;
	}

	//添加功能页面展示
	@RequestMapping("/add")
	public ModelAndView add(){
		ModelAndView view = new ModelAndView(this.viewPath+"add");
		return view;
	}

	//保存操作
	@ResponseBody
	@RequestMapping("/save")
	public Object save(Enterprise enterprise) {
		enterprise.setTime(new Date());
		if (commonDao.save(enterprise)) {
			return new ResultMessage(true, "添加成功!");
		} else {
			return new ResultMessage(true, "添加失败!");
		}
	}

	//删除
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") String rid){
		Boolean struts = commonDao.deleteByIds(Enterprise.class, rid);
		if(struts){
			return new ResultMessage(true, "删除成功！");
		}else{
			return new ResultMessage(false,"删除失败！");
		}
	}

	/**修改项目页面展示 */
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") long id) {
		ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("enterprise", commonDao.findById(Enterprise.class, id));
		return view;
	}

	/** 修改操作 */
	@ResponseBody
	@RequestMapping("/update")
	public Object update(@ModelAttribute("enterprise") Enterprise enterprise){
		enterprise.setTime(new Date());
		if (commonDao.update(enterprise)) {
			return new ResultMessage(true, "更新成功!");
		} else {
			return new ResultMessage(true, "更新失败!");
		}
	}
}
