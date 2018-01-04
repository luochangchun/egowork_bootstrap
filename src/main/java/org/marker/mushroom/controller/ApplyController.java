package org.marker.mushroom.controller;

import org.marker.mushroom.beans.InvestProject;
import org.marker.mushroom.beans.Investor;
import org.marker.mushroom.beans.NeedsApply;
import org.marker.mushroom.beans.OnlineApply;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.RoomApply;
import org.marker.mushroom.beans.SpaceApply;
import org.marker.mushroom.beans.TrainingApply;
import org.marker.mushroom.beans.VisitApply;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.StringUtil;
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
public class ApplyController extends SupportController {

	/**
	 * 共享会议室申请
	 *
	 * @param roomApply
	 * @return
	 */
	@PostMapping("/inster/roomapply")
	@ResponseBody
	public ResultMessage Roominster(final RoomApply roomApply) {
		roomApply.setTime(new Date());
		if (commonDao.save(roomApply)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败！");
		}

	}

	/**
	 * 预约参观申请
	 *
	 * @param visitApply
	 * @return
	 */
	@PostMapping("/inster/visitapply")
	@ResponseBody
	public ResultMessage Visitinster(final VisitApply visitApply) {
		visitApply.setTime(new Date());
		if (commonDao.save(visitApply)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败！");
		}
	}

	/**
	 * 线上孵化器表单提交
	 *
	 * @param onlineApply
	 * @return
	 */
	@PostMapping("/inster/onlineapply")
	@ResponseBody
	public ResultMessage Onlineinster(final OnlineApply onlineApply) {
		onlineApply.setTime(new Date());
		if (commonDao.save(onlineApply)) {
			return new ResultMessage(true, "申请成功!");
		} else {
			return new ResultMessage(false, "申请失败!");
		}
	}

	/**
	 * 租赁空间,众创空间
	 *
	 * @param spaceApply
	 * @return
	 */
	@PostMapping("/inster/tenancy")
	@ResponseBody
	public ResultMessage tenancyinster(final SpaceApply spaceApply) {

		int type = spaceApply.getType();
		String incubator = spaceApply.getIncubator();
		int incubatorId = spaceApply.getIncubatorId();
		int graduate = spaceApply.getGraduate();

		if (type <= 0) return new ResultMessage(false, "所选类型不能为空！");
		if (StringUtil.isBlank(incubator) || incubatorId <= 0)
			return new ResultMessage(false, "所属孵化器不能为空！");

		spaceApply.setTime(new Date());
		if (commonDao.save(spaceApply)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败！");
		}
	}

	/**
	 * 培训需求申请前台接口
	 */
	@RequestMapping("/inster/training")
	@ResponseBody
	public ResultMessage traininginster(final TrainingApply trainingApply) {
		trainingApply.setTime(new Date());
		if (commonDao.save(trainingApply)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败！");
		}
	}

	/**
	 * 服务需求申请
	 */
	@RequestMapping("/inster/needs")
	@ResponseBody
	public ResultMessage needsinster(final NeedsApply needsApply) {
		needsApply.setTime(new Date());
		if (commonDao.save(needsApply)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败！");
		}
	}

	/**
	 * 融资项目模块的申请
	 */
	@RequestMapping("/inster/invest")
	@ResponseBody
	public ResultMessage investinster(final InvestProject investProject) {
		investProject.setTime(new Date());
		investProject.setStatus(0);
		if (commonDao.save(investProject)) {
			return new ResultMessage(true, "申请成功！");
		} else {
			return new ResultMessage(false, "申请失败！");
		}
	}

	/**
	 * 投资机构申请
	 */
	@RequestMapping("/inster/ment")
	@ResponseBody
	public ResultMessage mentinster(final Investor investor){
		investor.setStatus(0);
		investor.setCreateTime(new Date());
		if(commonDao.save(investor)){
			return new ResultMessage(true,"申请成功!");
		}else{
			return new ResultMessage(false,"申请失败!");
		}
	}

}
