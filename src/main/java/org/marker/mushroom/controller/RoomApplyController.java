package org.marker.mushroom.controller;

import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.RoomApply;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 30 on 2017/12/18 0018.
 * 共享会议室申请
 */
@Controller
@RequestMapping("/admin/roomapply")
public class RoomApplyController extends SupportController{

	public RoomApplyController (){
		this.viewPath = "/admin/roomapply/";
	}

	//页面展示
	@RequestMapping("/list")
	public ModelAndView list(Page page){
		ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("page",commonDao.findByPage(page.getCurrentPageNo(),10,"select * from " +prefix+ "room_apply order by id desc"));
		return view;
	}

	//删除
	@RequestMapping("/delete")
	@ResponseBody
	public ResultMessage delete(@RequestParam("rid")String rid){
		Boolean struts = commonDao.deleteByIds(RoomApply.class,rid);
		if(struts){
			return new ResultMessage(true,"删除成功！");
		}else{
			return new ResultMessage(false, "删除失败！");
		}
	}

}


