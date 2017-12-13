package org.marker.mushroom.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.marker.mushroom.beans.Incubator;
import org.marker.mushroom.beans.Page;
import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.mushroom.dao.IArticleDao;
import org.marker.mushroom.service.impl.CategoryService;
import org.marker.mushroom.service.impl.DictionariesService;
import org.marker.mushroom.service.impl.IncubatorService;
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
@RequestMapping("/admin/incubator")
public class IncubatorController extends SupportController
{

	// 文章Dao
	@Autowired
	IArticleDao articleDao;

	@Autowired
	IncubatorService incubatorService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	DictionariesService dictionariesService;

	private static final Logger logger = LoggerFactory.getLogger(IncubatorController.class);

	/** 系统配置信息 */
	private final SystemConfig syscfg = SystemConfig.getInstance();

	private final String dbPrefix = DataBaseConfig.getInstance().getPrefix();

	public IncubatorController()
	{
		this.viewPath = "/admin/incubator/";

	}

	//发布文章
	@RequestMapping("/add")
	public ModelAndView add()
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "add");
		view.addObject("categorys", categoryService.list("incubator"));
		view.addObject("levels", dictionariesService.findDictionaries("level", "incubator"));
		view.addObject("labels", dictionariesService.findDictionaries("label", "incubator"));
		view.addObject("patterns", dictionariesService.findDictionaries("pattern", "incubator"));
		view.addObject("regions", dictionariesService.findDictionaries("region", "incubator"));

		return view;
	}

	//编辑文章
	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam("id") final int id)
	{
		final ModelAndView view = new ModelAndView(this.viewPath + "edit");
		view.addObject("incubator", commonDao.findById(Incubator.class, id));
		view.addObject("categorys", categoryService.list("incubator"));
		view.addObject("levels", dictionariesService.findDictionaries("level", "incubator"));
		view.addObject("labels", dictionariesService.findDictionaries("label", "incubator"));
		view.addObject("patterns", dictionariesService.findDictionaries("pattern", "incubator"));
		view.addObject("regions", dictionariesService.findDictionaries("region", "incubator"));

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
	public Object save(@ModelAttribute("incubator") final Incubator incubator, @RequestParam("cid") final int cid)
	{
		incubator.setTime(new Date());
		incubator.setCreateTime(new Date());
		incubator.setCid(cid);// 这里是因为不能注入bean里

		String msg = "";
		if (incubator.getStatus() == 1)
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

		if (commonDao.save(incubator))
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
	public Object update(@ModelAttribute("incubator") final Incubator incubator)
	{
		incubator.setTime(new Date());

		if (commonDao.update(incubator))
		{
			return new ResultMessage(true, "更新成功!");
		}
		else
		{
			return new ResultMessage(false, "更新失败!");
		}
	}

	//置顶
	@ResponseBody
	@RequestMapping("/top")
	public Object top(@RequestParam("ids") final String ids, @RequestParam("level") final String level)
	{
		String tips = "";
		if (commonDao.top(Incubator.class, ids, level))
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

	//删除文章
	@ResponseBody
	@RequestMapping("/delete")
	public Object delete(@RequestParam("rid") final String rid)
	{
		final boolean status = commonDao.deleteByIds(Incubator.class, rid);
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
		view.addObject("categorys", categoryService.list("incubator"));


		return view;
	}

	//	//审核文章列表
	//	@RequestMapping("/auditList")
	//	public ModelAndView auditList()
	//	{
	//		final ModelAndView view = new ModelAndView(this.viewPath + "audit/list");
	//		view.addObject("categorys", categoryService.list("incubator"));
	//		return view;
	//	}
	//
	//
	//	//编辑文章
	//	@RequestMapping("/auditEdit")
	//	public ModelAndView auditEdit(@RequestParam("id") final int id)
	//	{
	//		final ModelAndView view = new ModelAndView(this.viewPath + "audit/edit");
	//		view.addObject("incubator", commonDao.findById(Incubator.class, id));
	//		view.addObject("categorys", categoryService.list("incubator"));
	//		return view;
	//	}

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

		final Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("status", status);
		params.put("keyword", keyword);
		params.put("userid", userId);
		params.put("groupid", groupId);
		final Page page = incubatorService.find(currentPageNo, pageSize, params);

		final URLRewriteEngine urlRewrite = SingletonProxyFrontURLRewrite.getInstance();

		final String url = HttpUtils.getRequestURL(request);
		// 遍历URL重写
		for (final Map<String, Object> data : page.getData())
		{
			data.put("url", url + urlRewrite.encoder(data.get("url").toString()));
		}
		return page;
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
		final Page page = incubatorService.find(currentPageNo, pageSize);

		return page;
	}
}
