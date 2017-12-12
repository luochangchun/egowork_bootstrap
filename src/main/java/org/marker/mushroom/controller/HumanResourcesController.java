package org.marker.mushroom.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.marker.mushroom.beans.Human_resources;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.mushroom.dao.IArticleDao;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.service.impl.HumanResourcesService;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.urlrewrite.URLRewriteEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

;


/**
 * 文章管理
 * 
 * @author marker
 */
@Controller
@RequestMapping("/admin/humanResources")
public class HumanResourcesController extends SupportController
{

	// 文章Dao
	@Autowired
	IArticleDao articleDao;

	@Autowired
	HumanResourcesService humanResourcesService;

	@Autowired
	CategoryService categoryService;

	private static final Logger logger = LoggerFactory.getLogger(HumanResourcesController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public HumanResourcesController()
	{
		this.viewPath = "/admin/humanResources/";

	}

	//发布文章
	@RequestMapping("/add")
	public ModelAndView add()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		view.addObject("categorys", categoryService.list("humanResources"));
		return view;
	}

	//编辑文章
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("human_resources", commonDao.findById(Human_resources.class, id));
		view.addObject("categorys", categoryService.list("humanResources"));
		return view;
	}


	/**
	 * 持久化文章操作
	 * 
	 * @param article
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Object save(@ModelAttribute("human_resources") final Human_resources human_resources,
			@RequestParam("cid") final int cid)
	{
		human_resources.setTime(new Date());
		human_resources.setCreateTime(new Date());
		human_resources.setCid(cid);// 这里是因为不能注入bean里

		String msg = "";
		if (human_resources.getStatus() == 1)
		{
			msg = "发布";
		}
		else
		{
			msg = "保存草稿";
		}
		//		if (incubator.getType() == 1)
		//		{// marker
		//			try
		//			{
		//				final String orginalText = article.getContent();
		//				article.setOrginal(orginalText);
		//
		//				final String html = new Markdown4jProcessor().process(orginalText);
		//				article.setContent(html);
		//
		//			}
		//			catch (final IOException e)
		//			{
		//				e.printStackTrace();
		//			}
		//		}

		if (commonDao.save(human_resources))
		{
			return new ResultMessage(true, msg + "成功!");
		}
		else
		{
			return new ResultMessage(false, msg + "失败!");
		}
	}


	//保存
	@ResponseBody
	@RequestMapping("/update")
	public Object update(@ModelAttribute("human_resources") final Human_resources human_resources)
	{
		human_resources.setTime(new Date());

		if (commonDao.update(human_resources))
		{
			return new ResultMessage(true, "更新成功!");
		}
		else
		{
			return new ResultMessage(false, "更新失败!");
		}
	}


	//删除文章
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") final String rid)
	{
		final boolean status = commonDao.deleteByIds(Human_resources.class, rid);
		if (status)
		{
			return new ResultMessage(true, "删除成功!");
		}
		else
		{
			return new ResultMessage(false, "删除失败!");
		}
	}


	//发布文章
	@RequestMapping("/list")
	public ModelAndView listview(final HttpServletRequest request)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "list");
		view.addObject("categorys", categoryService.list("humanResources"));

		return view;
	}


	/**
	 * 文章列表接口(REST)
	 * 
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Object list(final HttpServletRequest request, final ModelMap model,
			@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("cid") String cid,
			@RequestParam("status") final String status, @RequestParam("keyword") final String keyword,
			@RequestParam("pageSize") final int pageSize

	)
	{
		final HttpSession session = request.getSession();
		final String usercategory = (String) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_CATEGORY);
		final Integer userId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSION_USER_ID);
		final Integer groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);

		if (StringUtils.isEmpty(cid))
		{
			cid = usercategory;
		}

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("cid", cid);
		params.put("status", status);
		params.put("keyword", keyword);
		params.put("userid", userId);
		params.put("groupid", groupId);
		final Page page = humanResourcesService.find(currentPageNo, pageSize, params);

		final URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		final String url = HttpUtils.getRequestURL(request);
		// 遍历URL重写
		for (final Map<String, Object> data : page.getData())
		{
			data.put("url", url + urlRewrite.encoder(data.get("url").toString()));
		}
		return page;
	}

	//置顶
	@ResponseBody
	@RequestMapping("/top")
	public Object top(@RequestParam("ids") final String ids, @RequestParam("level") final String level)
	{
		String tips = "";
		if (commonDao.top(Human_resources.class, ids, level))
		{
			if (level.equals("1"))
				tips = "置顶成功！";
			else if (level.equals("0"))
				tips = "取消置顶成功！";

			return new ResultMessage(true, tips);
		}
		else
		{
			if (level.equals("1"))
				tips = "置顶失败！";
			else if (level.equals("0"))
				tips = "取消置顶失败！";

			return new ResultMessage(false, tips);
		}
	}

	@RequestMapping("/view")
	public ModelAndView view()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "applyList");
		return view;

	}

	/**
	 * 志愿者个人列表接口(REST)
	 *
	 * @param currentPageNo
	 * @return
	 */
	@RequestMapping(value = "/applyList", method = RequestMethod.GET)
	@ResponseBody
	public Object applyList(@RequestParam("currentPageNo") final int currentPageNo, @RequestParam("pageSize") final int pageSize)
	{
		final Page page = humanResourcesService.find(currentPageNo, pageSize);

		return page;
	}
}
