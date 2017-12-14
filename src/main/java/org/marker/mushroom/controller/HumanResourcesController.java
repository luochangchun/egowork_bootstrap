package org.marker.mushroom.controller;

import org.marker.mushroom.beans.HumanResources;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.service.impl.HumanResourcesService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.urlrewrite.URLRewriteEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

;

/**
 * 文章管理
 *
 * @author marker
 */
@Controller
@RequestMapping("/admin/humanResources")
public class HumanResourcesController extends SupportController {

	@Autowired
	HumanResourcesService humanResourcesService;

	@Autowired
	CategoryService categoryService;

	public HumanResourcesController() {
		this.viewPath = "/admin/humanResources/";
	}

	//发布文章
	@RequestMapping("/add")
	public ModelAndView add() {
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		view.addObject("categorys", categoryService.list("humanResources"));
		return view;
	}

	//编辑文章
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("human_resources", commonDao.findById(HumanResources.class, id));
		view.addObject("categorys", categoryService.list("humanResources"));
		return view;
	}

	@ResponseBody
	@RequestMapping("/save")
	public Object save(@ModelAttribute("human_resources") final HumanResources humanResources,
					   @RequestParam("cid") final int cid) {
		humanResources.setTime(new Date());
		humanResources.setCreateTime(new Date());
		humanResources.setCid(cid);// 这里是因为不能注入bean里

		String msg;
		if (humanResources.getStatus() == 1) {
			msg = "发布";
		} else {
			msg = "保存草稿";
		}

		if (commonDao.save(humanResources)) {
			return new ResultMessage(true, msg + "成功!");
		} else {
			return new ResultMessage(false, msg + "失败!");
		}
	}

	//保存
	@ResponseBody
	@RequestMapping("/update")
	public Object update(@ModelAttribute("human_resources") final HumanResources humanResources) {
		humanResources.setTime(new Date());

		if (commonDao.update(humanResources)) {
			return new ResultMessage(true, "更新成功!");
		} else {
			return new ResultMessage(false, "更新失败!");
		}
	}

	//删除文章
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") final String rid) {
		final boolean status = commonDao.deleteByIds(HumanResources.class, rid);
		if (status) {
			return new ResultMessage(true, "删除成功!");
		} else {
			return new ResultMessage(false, "删除失败!");
		}
	}

	//发布文章
	@RequestMapping("/list")
	public ModelAndView listview(final HttpServletRequest request) {
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("categorys", categoryService.list("humanResources"));

		return view;
	}

	@GetMapping
	@ResponseBody
	public Object list(final HttpServletRequest request,
					   final ModelMap model,
					   @RequestParam("currentPageNo") final int currentPageNo,
					   @RequestParam("cid") String cid,
					   @RequestParam("status") final String status,
					   @RequestParam("keyword") final String keyword,
					   @RequestParam("pageSize") final int pageSize) {
		final HttpSession session = request.getSession();
		final String usercategory = (String) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_CATEGORY);
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);
		final Integer groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);

		if (StringUtils.isEmpty(cid)) {
			cid = usercategory;
		}

		final Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("status", status);
		params.put("keyword", keyword);
		params.put("userid", userId);
		params.put("groupid", groupId);
		final Page page = humanResourcesService.find(currentPageNo, pageSize, params);

		final URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		final String url = HttpUtils.getRequestURL(request);
		// 遍历URL重写
		for (final Map<String, Object> data : page.getData()) {
			data.put("url", url + urlRewrite.encoder(data.get("url").toString()));
		}
		return page;
	}

	//置顶
	@ResponseBody
	@RequestMapping("/top")
	public Object top(@RequestParam("ids") final String ids, @RequestParam("level") final String level) {
		String tips = "";
		if (commonDao.top(HumanResources.class, ids, level)) {
			if (level.equals("1"))
				tips = "置顶成功！";
			else if (level.equals("0"))
				tips = "取消置顶成功！";

			return new ResultMessage(true, tips);
		} else {
			if (level.equals("1"))
				tips = "置顶失败！";
			else if (level.equals("0"))
				tips = "取消置顶失败！";

			return new ResultMessage(false, tips);
		}
	}

	@RequestMapping("/view")
	public ModelAndView view() {
		return new ModelAndView(this.viewPath + "applyList");
	}

	/**
	 * 志愿者个人列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/applyList", method = RequestMethod.GET)
	@ResponseBody
	public Object applyList(@RequestParam("currentPageNo") final int currentPageNo,
							@RequestParam("pageSize") final int pageSize) {
		return humanResourcesService.find(currentPageNo, pageSize);
	}
}
