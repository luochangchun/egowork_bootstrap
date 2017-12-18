package org.marker.mushroom.controller;

import org.marker.mushroom.beans.OnlineApply;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.RoomApply;
import org.marker.mushroom.beans.SpaceApply;
import org.marker.mushroom.beans.VisitApply;
import org.marker.mushroom.support.SupportController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by 30 on 2017/12/18 0018.
 * 前端接口，共享会议室申请、预约参观表查看、线上孵化器查看添加接口
 */
@Controller
@RequestMapping("/front/Apply")
public class ApplyController extends SupportController{


	//共享会议室申请
	@PostMapping("/inster/roomapply")
	@ResponseBody
	public Object Roominster(final RoomApply roomApply){
		roomApply.setTime(new Date());
		if(commonDao.save(roomApply)){
			return new ResultMessage(true , "申请成功！");
		}else {
			return new ResultMessage(false , "申请失败！");
		}

	}

	//预约参观申请
	@PostMapping("/inster/visitapply")
	@ResponseBody
	public Object Visitinster(final VisitApply visitApply){
		visitApply.setTime(new Date());
		if(commonDao.save(visitApply)){
			return new ResultMessage(true , "申请成功！");
		}else{
			return new ResultMessage(false , "申请失败！");
		}
	}

	//线上孵化器表单提交
	@PostMapping("/inster/onlineapply")
	@ResponseBody
	public Object Onlineinster(final OnlineApply onlineApply){
		onlineApply.setTime(new Date());
		if(commonDao.save(onlineApply)){
			return new ResultMessage(true , "申请成功!");
		}else {
			return new ResultMessage(false , "申请失败!");
		}
	}

	//租赁空间,众创空间
	@PostMapping("/inster/tenancy")
	@ResponseBody
	public Object tenancyinster(final SpaceApply spaceApply){

		Integer type=spaceApply.getType();
		String incubator = spaceApply.getIncubator();
		Integer incubatorId=spaceApply.getIncubatorId();

		if(type!=null&&incubator!=null&&incubatorId!=null){
			if(commonDao.save(spaceApply)){
				return new ResultMessage(true,"申请成功！");
			}else{
				return new ResultMessage(false,"申请失败！");
			}
		}else{
			return new ResultMessage(false,"所属孵化器不能为空！");
		}
	}


}
