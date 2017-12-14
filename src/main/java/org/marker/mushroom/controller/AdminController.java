package org.marker.mushroom.controller;

import org.marker.mushroom.beans.ResultMessage;
import org.marker.mushroom.beans.User;
import org.marker.mushroom.beans.UserLoginLog;
import org.marker.mushroom.beans.UserObject;
import org.marker.mushroom.core.AppStatic;
import org.marker.mushroom.core.WebAPP;
import org.marker.mushroom.dao.IMenuDao;
import org.marker.mushroom.dao.IPermissionDao;
import org.marker.mushroom.dao.IUserDao;
import org.marker.mushroom.dao.IUserLoginLogDao;
import org.marker.mushroom.support.SupportController;
import org.marker.mushroom.utils.GeneratePass;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.qqwryip.IPLocation;
import org.marker.qqwryip.IPTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 后台管理主界面控制器
 *
 * @author marker
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends SupportController {

	@Autowired
	IUserDao userDao;
	@Autowired
	IUserLoginLogDao userLoginLogDao;
	@Autowired
	IMenuDao menuDao;
	@Autowired
	ServletContext application;

	@Autowired
	IPermissionDao permissionDao;

	/** 构造方法初始化一些成员变量 */
	public AdminController() {
		this.viewPath = "/admin/";
	}

	/** 后台主界面 */
	@RequestMapping("/index")
	public String index(final HttpServletRequest request) {
		// 如果没有安装系统
		if (!WebAPP.install)
			return "redirect:../install/index.jsp";

		request.setAttribute("url", HttpUtils.getRequestURL(request));
		final HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				final int groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);
				request.setAttribute("topmenus", menuDao.findTopMenuByGroupId(groupId));
			} catch (final Exception e) {
				log.error("因为没有登录，在主页就不能查询到分组ID");
				return "redirect:login.do";
			}
		}

		return this.viewPath + "index";
	}

	/**
	 * 子菜单接口
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/childmenus")
	@ResponseBody
	public Object menu(final HttpServletRequest request, @RequestParam("id") final int id) {
		final ModelAndView view = new ModelAndView(this.viewPath + "childmenus");
		view.addObject("menu", menuDao.findMenuById(id));
		final HttpSession session = request.getSession();
		if (session != null) {
			try {
				final int groupId = (Integer) session.getAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID);
				view.addObject("childmenus", menuDao.findChildMenuByGroupAndParentId(groupId, id));
			} catch (final Exception e) {
				log.error("因为没有登录，在主页就不能查询到分组ID");
				return "<script>window.location.href='login.do?status=timeout';</script>";
			}
		}
		return view;
	}

	/**
	 * 登录操作
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(final HttpServletRequest request) {
		// 如果没有安装系统
		if (!WebAPP.install)
			return "redirect:../install/index.jsp";

		request.setAttribute("url", HttpUtils.getRequestURL(request));

		final HttpSession session = request.getSession(false);
		if (session != null) {
			try {
				final User user = (User) session.getAttribute(AppStatic.WEB_APP_SESSION_ADMIN);
				if (null != user) {
					return "redirect:index.do";
				}
			} catch (final Exception ignored) { }
		}
		return this.viewPath + "login";
	}

	/**
	 * 登录系统 验证码不区分大小写
	 *
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/loginSystem", method = RequestMethod.POST)
	public Object loginSystem(final HttpServletRequest request) {
		final String randcode = request.getParameter("randcode").toLowerCase();//验证码
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final String device = request.getParameter("device");// 设备
		final HttpSession session = request.getSession();// 如果会话不存在也就创建
		final Object authCode = session.getAttribute(AppStatic.WEB_APP_AUTH_CODE);

		int errorCode = 0;// 登录日志类型
		String scode = "";
		if (authCode != null) {
			scode = ((String) authCode).toLowerCase();
		}

		ResultMessage msg;
		if (!scode.equals(randcode)) {// 验证码不匹配
			msg = new ResultMessage(false, "验证码错误!");
			errorCode = 1;// 错误
		} else {
			String password2;
			try {
				password2 = GeneratePass.encode(password);
				final User user = userDao.queryByNameAndPass(username, password2);
				if (user != null) {
					if (user.getStatus() == 1) {//启用
						userDao.updateLoginTime(user.getId());// 更新登录时间
						session.setAttribute(AppStatic.WEB_APP_SESSION_ADMIN, user);
						session.setAttribute(AppStatic.WEB_APP_SESSSION_LOGINNAME, user.getName());
						session.setAttribute(AppStatic.WEB_APP_SESSSION_USER_GROUP_ID, user.getGid());// 设置分组
						session.setAttribute(AppStatic.WEB_APP_SESSION_USER_ID, user.getId());
						String button = "";
						String category = "";

						final List<UserObject> userButtonList = permissionDao.findUserObjectByGroupId(user.getGid(), "button");
						for (final UserObject object : userButtonList) {
							button += object.getOid() + ",";
						}
						final List<UserObject> userCategoryList =
							permissionDao.findUserObjectByGroupId(user.getGid(), "category");
						for (final UserObject object : userCategoryList) {
							category += object.getOid() + ",";
						}

						if (!StringUtils.isEmpty(button) && button.endsWith(",")) {
							button = button.substring(0, button.length() - 1);
						}
						if (!StringUtils.isEmpty(category) && category.endsWith(",")) {
							category = category.substring(0, category.length() - 1);
						}
						session.setAttribute(AppStatic.WEB_APP_SESSION_USER_BUTTION, button);
						session.setAttribute(AppStatic.WEB_APP_SESSION_USER_CATEGORY, category);

						session.removeAttribute(AppStatic.WEB_APP_AUTH_CODE);//移除验证码

						msg = new ResultMessage(true, "登录成功!");
					} else {
						errorCode = 1;
						msg = new ResultMessage(false, "用户已禁止登录!");
					}
				} else {
					errorCode = 1;
					msg = new ResultMessage(false, "用户名或者密码错误!");
				}
			} catch (final Exception e) {
				errorCode = 1;
				msg = new ResultMessage(false, "系统加密算法异常!");
				log.error("系统加密算法异常!", e);
			}

		}
		// 获取真实IP地址
		final String ip = HttpUtils.getRemoteHost(request);

		// IP归属地获取工具
		final IPTool ipTool = IPTool.getInstance();

		// 记录日志信息
		final UserLoginLog loginLog = new UserLoginLog();
		loginLog.setUsername(username);
		loginLog.setTime(new Date());

		loginLog.setDevice(device);
		loginLog.setInfo(msg.getMessage());
		loginLog.setIp(ip);
		loginLog.setErrorcode(errorCode);
		if (ip != null) {
			try {
				final IPLocation location = ipTool.getLocation(ip);
				if (location != null) {// 如果存在
					loginLog.setArea(location.getCountry());
				}
			} catch (final Exception e) {
				log.error("ip={} ", ip, e);
			}
		}

		userLoginLogDao.save(log);

		return msg;
	}

	/**
	 * 注销
	 */
	@RequestMapping("/logout")
	public String logout(final HttpServletRequest request, final HttpServletResponse response) {
		final HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();
		return "redirect:login.do";
	}

	/**
	 * 系统信息
	 */
	@RequestMapping("/systeminfo")
	public ModelAndView systeminfo() {
		final ModelAndView view = new ModelAndView(this.viewPath + "systeminfo");
		final String os = System.getProperty("os.name");//操作系统名称
		final String osVer = System.getProperty("os.version"); //操作系统版本    
		final String javaVer = System.getProperty("java.version"); //操作系统版本
		final String javaVendor = System.getProperty("java.vendor"); //操作系统版本

		final Runtime runTime = Runtime.getRuntime();

		final long freeM = runTime.freeMemory() / 1024 / 1024;
		final long maxM = runTime.maxMemory() / 1024 / 1024;
		final long tM = runTime.totalMemory() / 1024 / 1024;
		view.addObject("freememory", freeM);
		view.addObject("maxmemory", maxM);
		view.addObject("totalmemory", tM);
		view.addObject("os", os);
		view.addObject("osver", osVer);
		view.addObject("javaver", javaVer);
		view.addObject("javavendor", javaVendor);
		view.addObject("currenttime", new Date());

		view.addObject("serverinfo", application.getServerInfo());
		view.addObject("dauthor", "marker");
		view.addObject("email", "wuweiit@gmail.com");
		view.addObject("version", "20140803");
		view.addObject("qqqun", "331925386");
		view.addObject("uxqqqun", "181150189");

		return view;
	}

}
