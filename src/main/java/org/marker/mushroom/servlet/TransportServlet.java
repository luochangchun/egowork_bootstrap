package org.marker.mushroom.servlet;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.User_regist_log;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.dto.Pager;
import org.marker.mushroom.dto.UserData;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.utils.ApplicationConstant;
import org.marker.mushroom.utils.CSRFUtil;
import org.marker.mushroom.utils.HttpUtils;
import org.marker.mushroom.utils.MemCachedManager;
import org.marker.mushroom.utils.PropertyUtil;
import org.marker.mushroom.utils.SpringContextUtil;
import org.marker.mushroom.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

public class TransportServlet extends HttpServlet {

	private static final long serialVersionUID = -266565810999894402L;

	private static final Logger logger = LoggerFactory.getLogger(TransportServlet.class);

	private static final MemCachedManager mcm = MemCachedManager.getInstance();

	private static final PropertyUtil propertyUtil = (PropertyUtil) SpringContextUtil.getBean("propertyUtil");

	@Override
	public void destroy() {
	}

	@Override
	public void doPut(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
																								   IOException {
		doPost(request, response);
	}

	@Override
	public void doDelete(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException,
																								   IOException {
		doPost(request, response);
	}

	/*
	 * 
	 * 通常 rest 请求分为 以下几大类: 1. 以/access 开头的 前端请求(包括注册, 登录, 注销请求) 2. 以/app 开头的 控制台请求 3. 特殊请求 以#/download开头之类 等
	 * 
	 * 第1类请求不需要登录, 即无需校验 session, 而第2类请求必须检查session,如果session 为空 , 返回 419响应码, 由angular state管理转向登录;
	 * 
	 * 请求处理过程 如下: 1.解析各类参数 2.根据上面的请求类型做相应的处理 a.登录请求成功以后, 保存到session , 注销请求成功以后则清除session信息 b.过滤掉特殊请求
	 * 
	 * 3.发送请求 如果session 不为空,则session数据会附带发送
	 */

	@SuppressWarnings("unchecked")
	@Override
	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
		throws ServletException, IOException {
		String sessionid;
		String ip;
		UserData currentUser = null;
		final Gson gs = new Gson();

		request.setCharacterEncoding("UTF-8");
		final String type = request.getParameter("type");
		String url = request.getParameter("resource");
		// String module = request.getParameter("module");
		String param = request.getParameter("param");
		param = StringUtils.isBlank(param) ? "" : new String(request.getParameter("param").getBytes("UTF-8"), "UTF-8");
		sessionid = request.getSession().getId();
		// ip = request.getLocalAddr();
		ip = HttpUtils.getRemoteHost(request);

		final String REST_BASE_URL = propertyUtil.getValue("sys.rest_url");
		final String SESSION_TIMEOUT = propertyUtil.getValue("sys.session_timeout");

		JSONObject jsonObject = null;
		if (!StringUtils.isBlank(param)) {
			jsonObject = JSONObject.fromObject(param);
			if (StringUtil.isBlankStr(url) && jsonObject.containsKey("resource"))
				url = jsonObject.getString("resource");
		}
		// String resource = jsonObject.getString("resource");
		//String module = jsonObject.getString("module");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		/*
		 * 发送验证码次数校验
		 */
		if (isSendCodeURL(url) || isForgotPwdURL(url)) {
			//CSRF校验
			final JSONObject csrf_result = checkCSRF(request, sessionid);
			if (csrf_result.getInt("code") != 200) {
				response.getWriter().write(csrf_result.toString());
				return;
			}

			final int count = mcm.get("SEND_CODE_LIMIT_" + ip) == null ? 0 : (Integer) mcm.get("SEND_CODE_LIMIT_" + ip);
			logger.debug("--------resource:" + url + "checkSendCodeLimit: count - " + count + ",ip:" + ip);
			if (count >= propertyUtil.getIntValue("sys.ip_send_code_limit") && !"27.17.54.74".equals(ip)) {
				final JSONObject result = new JSONObject();
				result.put("code", 401);
				result.put("result", "发送验证码超过次数限制,请联系客服:" + propertyUtil.getValue("sys.cust_manage_tel"));
				result.put("message", "发送验证码超过次数限制,请联系客服:" + propertyUtil.getValue("sys.cust_manage_tel"));
				response.getWriter().write(result.toString());
				return;
			}
		}

		/*
		 * 注册url 校验手机验证码
		 */
		if (isRegistURL(url) || isUpdateUserPhoneURL(url) || isFindPwdByPhoneURL(url)) {
			/*
			 * 判断参数不为空
			 */
			if (jsonObject != null && !jsonObject.isEmpty() && !jsonObject.isNullObject()) {
				final JSONObject userObj = jsonObject.getJSONObject("fields");
				if (!userObj.isEmpty() && !userObj.isNullObject()) {
					final JSONObject checkResult = checkPhoneCode(userObj);
					//验证码校验不通过
					if (checkResult.getInt("code") != 200) {
						response.getWriter().write(checkResult.toString());
						return;
					}
				}
			}
		}

		// 初始化CSRF
		initCSRFInfo(sessionid, response, false);

		Cache cache = DynaCacheService.getContent(sessionid + ApplicationConstant.USER_CACHE_SUFFIX);
		if (cache == null) {
			cache = (Cache) mcm.get(sessionid + ApplicationConstant.USER_CACHE_SUFFIX);
			if ((cache == null) || (cache.isExpired())) {
				cache = null;
			}
		}
		if (null != cache) {
			cache.setTimeOut(Long.parseLong(SESSION_TIMEOUT) * 1000 + new Date().getTime());
			mcm.set(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, cache, Integer.parseInt(SESSION_TIMEOUT));
			currentUser = (UserData) cache.getValue();
			logger.debug("++++++++++++++++++++resource:" + url + "sessionId:" + sessionid + ",ip:" + ip + ",userId:"
							 + currentUser.getUserId() + "++++++++++++++++++++++++");
		} else {
			logger.debug(
				"++++++++++++++++++++resource:" + url + "sessionId:" + sessionid + ",ip:" + ip + "++++++++++++++++++++++++");
		}

		// 控制台请求需要做session 检查
		//if (isConsoleURL(module) && null == currentUser && !isLoginURL(url)) {
		if (null == currentUser && !isLoginURL(url) && !isRefreshURL(url)) {
			logger.debug("++++++++++++++++++++session check fail++++++++++++++++++++");
			/*
			 * response.setStatus(419); return ;
			 */
		} else if ("checkSession".equals(url) && currentUser != null) {
			final String entity = gs.toJson(currentUser);
			response.getWriter().write(entity);
			response.setStatus(200);
			return;
		}
		// 注销请求处理 清除会话
		/*
		 * if (isLogoutURL(url)) { DynaCacheService.invalidate(sessionid + ip); response.getWriter().write("{'code':200");
		 * response.setStatus(200); } else
		 */

		if (isRefreshURL(url)) {// 从缓存获取session
			initCSRFInfo(sessionid, response, true);
			if (currentUser == null) {
				currentUser = new UserData();
			}
			final String entity = gs.toJson(currentUser);
			response.getWriter().write(entity);
			response.setStatus(200);
		} else {

			if (isLogoutURL(url)) {
				mcm.remove(sessionid + ApplicationConstant.USER_CACHE_SUFFIX);
				DynaCacheService.invalidate(sessionid + ApplicationConstant.USER_CACHE_SUFFIX);
			}

			// 发送rest请求
			final ClientResponse myresponse = send(REST_BASE_URL, type, url, param, ip, currentUser);

			logger.debug("" + myresponse.getStatus());
			logger.debug("" + myresponse.getHeaders().get("Content-Type"));

			String entity = myresponse.getEntity(String.class);
			logger.debug(entity);

			if ((Integer) JSONObject.fromObject(entity).get("code") == 419) {
				logger.debug("++++++++++++++++++++session check fail++++++++++++++++++++");
				response.setStatus(419);
				return;
			}

			/*
			 * 注册url 注册成功清空 缓存 手机验证码
			 */
			if ((isRegistURL(url) || isUpdateUserPhoneURL(url) || isFindPwdByPhoneURL(url))
				&& (Integer) JSONObject.fromObject(entity).get("code") == 200) {
				/*
				 * 判断参数不为空
				 */
				if (jsonObject != null && !jsonObject.isEmpty() && !jsonObject.isNullObject()) {
					final JSONObject userObj = jsonObject.getJSONObject("fields");
					if (!userObj.isEmpty() && !userObj.isNullObject()) {
						mcm.remove(userObj.getString("phone"));

						final ISupportDao dao = SpringContextHolder.getBean(DAO.COMMON);
						final User_regist_log user_regist_log = new User_regist_log();
						user_regist_log.setPhone(userObj.getString("phone"));
						user_regist_log.setTime(new Date());
						dao.save(user_regist_log);
					}
				}

			}

			/*
			 * 发送请求成功以后处理步骤: 对于特殊需求 /download 直接response 对于登录成功以后则需要写入session
			 */
//			if (isDownloadURL(url)) { }

			Cache c;
			if (isRefreshSession(url)) { // 强制刷新缓存
				mcm.remove(sessionid + ApplicationConstant.USER_CACHE_SUFFIX);
				DynaCacheService.invalidate(sessionid + ApplicationConstant.USER_CACHE_SUFFIX);
				final Pager pager = gs.fromJson(entity, new TypeToken<Pager>() {}.getType());
				final LinkedTreeMap<String, Object> r = (LinkedTreeMap<String, Object>) pager.getResult();
				if (r != null) {
					loginCMS(r.get("userName").toString(), request, response);
					currentUser = getUserData(r);
					c = DynaCacheService.putContent(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, currentUser,
													Long.parseLong(SESSION_TIMEOUT));
					mcm.set(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, c, Integer.parseInt(SESSION_TIMEOUT));

					entity = "{\"code\":200,\"result\":" + gs.toJson(currentUser) + "}";
				}
			}
			if (isLoginURL(url)) {
				// Pager pager = gs.fromJson(entity, Pager.class);
				final Pager pager = gs.fromJson(entity, new TypeToken<Pager>() {}.getType());
				final LinkedTreeMap<String, Object> r = (LinkedTreeMap<String, Object>) pager.getResult();
				if (r != null) {
					loginCMS(r.get("userName").toString(), request, response);
					currentUser = getUserData(r);
					c = DynaCacheService.putContent(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, currentUser,
													Long.parseLong(SESSION_TIMEOUT));
					mcm.set(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, c, Integer.parseInt(SESSION_TIMEOUT));
					entity = "{\"code\":200,\"result\":" + gs.toJson(currentUser) + "}";
					mcm.remove(sessionid + "_USERID_LOGIN_ERROR_CON");
				} else if (pager.getCode() == 500) {
					Integer loginErrorCon = mcm.get(sessionid + "_USERID_LOGIN_ERROR_CON") == null ? 0
						: (Integer) mcm.get(sessionid + "_USERID_LOGIN_ERROR_CON");
					loginErrorCon = loginErrorCon + 1;
					if (loginErrorCon > 2) {
						entity = "{\"code\":502,\"message\":\"用户名或密码错误\"}";
					}
					mcm.set(sessionid + "_USERID_LOGIN_ERROR_CON", loginErrorCon);
					System.out.println(mcm.get(sessionid + "_USERID_LOGIN_ERROR_CON"));
				}
				// 单点登录
				if (isSingleLoginURL(url)) {
					final String s = request.getRequestURI();
					request.getRequestDispatcher("/index.html").forward(request, response);
					return;
				}
			}
			if (isUserInit(url)) {
				final Pager pager = gs.fromJson(entity, new TypeToken<Pager>() {}.getType());
				final LinkedTreeMap<String, Object> r = (LinkedTreeMap<String, Object>) pager.getResult();
				if (r != null) {
					loginCMS(r.get("userName").toString(), request, response);
					currentUser = getUserData(r);
					c = DynaCacheService.putContent(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, currentUser,
													Long.parseLong(SESSION_TIMEOUT));
					mcm.set(sessionid + ApplicationConstant.USER_CACHE_SUFFIX, c, Integer.parseInt(SESSION_TIMEOUT));
					entity = "{\"code\":200,\"result\":" + gs.toJson(currentUser) + "}";
				}
			}

			/*
			 * 发送手机验证码后放入缓存
			 */
			if (isSendCodeURL(url) || isForgotPwdURL(url)) {
				int count = mcm.get("SEND_CODE_LIMIT_" + ip) == null ? 0 : (Integer) mcm.get("SEND_CODE_LIMIT_" + ip);
				count++;
				final int sendcode_limit_time = propertyUtil.getIntValue("sys.sendcode_limit_time");
				mcm.set("SEND_CODE_LIMIT_" + ip, count, sendcode_limit_time);

				final Pager pager = gs.fromJson(entity, new TypeToken<Pager>() {}.getType());
				final String cacheResult = cachePhoneCode(url, pager);
				if (cacheResult != null)
					entity = cacheResult;
			}
			if (currentUser != null) {
				request.getSession().setAttribute("currentUser", entity);
			}
			response.getWriter().write(entity);

		}
	}

	/**
	 * @param urlString
	 * @param method
	 * @param resource
	 * @param param
	 * @return
	 * @throws IOException
	 */
	private ClientResponse send(final String urlString, final String method, final String resource, final String param,
								final String ip, final UserData currentUser) throws IOException {

		final Client c = Client.create();
		ClientResponse response = null;

		try {
			final WebResource r = c.resource(urlString + URLEncoder.encode(resource, "UTF-8").replace("%2F", "/"));
			final MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
			if (param != null)
				queryParams.add("param", URLEncoder.encode(param, "UTF-8").replace("+", "%20"));
			queryParams.add("ip", ip);
			final Gson gs = new Gson();
			if (null != currentUser) {
				queryParams.add("userData", gs.toJson(currentUser));
			}

			final Builder _r = r.queryParams(queryParams).header("Content-Type", "application/json;charset=UTF-8")
								.accept(MediaType.APPLICATION_JSON_TYPE);

			if (method.equalsIgnoreCase("POST")) {
				response = _r.post(ClientResponse.class);
			}

			if (method.equalsIgnoreCase("GET")) {
				response = _r.get(ClientResponse.class);
			}

			if (method.equalsIgnoreCase("PUT")) {
				response = _r.put(ClientResponse.class);
			}

			if (method.equalsIgnoreCase("DELETE")) {
				response = _r.delete(ClientResponse.class);
			}

			if (method.equalsIgnoreCase("ACTION")) {
				response = _r.post(ClientResponse.class);
			}

			return response;
		} finally {
			c.destroy();
		}

	}

	private void loginCMS(final String username, final HttpServletRequest request, final HttpServletResponse response)
		throws IOException {
		final String host = request.getServerName();
		final Cookie cookie = new Cookie("SESSION_LOGIN_USERNAME", username); // 保存用户名到Cookie
		cookie.setDomain(host);
		// cookie.setPath("/");
		cookie.setPath("/wisme-cms");
		cookie.setMaxAge(3000);// 15分钟
		response.addCookie(cookie);
	}

	private boolean isDownloadURL(final String resource) {

		return StringUtils.contains(resource, "resource/download/");
	}

	private boolean isConsoleURL(final String module) {

		return StringUtils.contains(module, "#/app");
	}

	private boolean isLoginURL(final String resource) {

		return StringUtils.contains(resource, "/login") || StringUtils.contains(resource, "/singleLogin");
	}

	private boolean isSingleLoginURL(final String resource) {
		return StringUtils.contains(resource, "/singleLogin");
	}

	private boolean isLogoutURL(final String resource) {

		return StringUtils.contains(resource, "/logout");
	}

	private boolean isRefreshURL(final String resource) {

		return StringUtils.contains(resource, "/getUserInSession");
	}

	/**
	 * 注册url
	 *
	 * @param resource
	 * @return
	 */
	private boolean isRegistURL(final String resource) {
		return StringUtils.contains(resource, "/personalRegist") || StringUtils.contains(resource, "/newRegist");
	}

	/**
	 * 忘记密码(通过手机找回密码)url
	 *
	 * @param resource
	 * @return
	 */
	private boolean isForgotPwdURL(final String resource) {
		return StringUtils.contains(resource, "/sendPwdCode");
	}

	/**
	 * 通过手机找回密码url
	 *
	 * @param resource
	 * @return
	 */
	private boolean isFindPwdByPhoneURL(final String resource) {
		return resource.startsWith("signup/") && resource.endsWith("/modifyPwd");
	}

	/**
	 * 更新用户手机url
	 *
	 * @param resource
	 * @return
	 */
	private boolean isUpdateUserPhoneURL(final String resource) {
		return resource.startsWith("user/") && resource.endsWith("/updatePhone");
	}

	/**
	 * 发送手机验证码url
	 *
	 * @param resource
	 * @return
	 */
	private boolean isSendCodeURL(final String resource) {
		return StringUtils.contains(resource, "/sendCode");
	}

	private boolean isRefreshSession(final String resource) {
		return StringUtils.contains(resource, "/forceFrash");
	}

	private boolean isUserInit(final String resource) {
		// TODO Auto-generated method stub
		return (StringUtils.contains(resource, "/CompanyUser")//初始为企业用户
			|| StringUtils.contains(resource, "/CompanyPersonnelUser")//初始为个人用户
			|| StringUtils.contains(resource, "/personalUser")//初始为企业员工
			|| StringUtils.contains(resource, "/dindMailbox")//已初始化邮箱
			|| StringUtils.contains(resource, "/noInit")//未初始化
		);
	}

	/**
	 * 转换登陆用户信息
	 *
	 * @param r
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private UserData getUserData(final LinkedTreeMap<String, Object> r) {
		final UserData currentUser = new UserData();
		final LinkedTreeMap<String, Object> company = (LinkedTreeMap<String, Object>) r.get("company");
		final List<LinkedTreeMap<String, Object>> agentProducts = (List<LinkedTreeMap<String, Object>>) r.get("agentProducts");

		currentUser.setUserId((int) (Double.parseDouble(r.get("userId") == null ? "0" : r.get("userId").toString())));
		if (currentUser.getUserId() > 0)
			currentUser.setReferrerCode(currentUser.getUserId());
		currentUser.setUserName(r.get("userName").toString());
		currentUser.setUserType((int) (Double.parseDouble(r.get("userType") == null ? "0" : r.get("userType").toString())));
		currentUser.setCompanyId((int) (Double.parseDouble(r.get("companyId") == null ? "0" : r.get("companyId").toString())));

		currentUser.setAgentId((int) (Double.parseDouble(r.get("agentId") == null ? "0" : r.get("agentId").toString())));
		if (company != null) {
			if (company.get("imglogo") != null) {
				currentUser.setCompanyLogo(company.get("imglogo").toString());
			}
			if (company.get("abbreviationName") != null) {
				currentUser.setAbbreviationName(company.get("abbreviationName").toString());
			}
			if (company.get("vipLevel") != null) {
				currentUser.setVipLevel(((Double) company.get("vipLevel")).intValue());
			}
			if (company.get("adminId") != null) {
				currentUser.setAdminId(((Double) company.get("adminId")).intValue());
			}
		}
		if (agentProducts != null) {
			String agentPros = "";
			for (int i = 0; i < agentProducts.size(); i++) {
				final LinkedTreeMap<String, Object> each = agentProducts.get(i);
				if (i == agentProducts.size() - 1) {
					agentPros += each.get("productName").toString();
				} else {
					agentPros += each.get("productName").toString() + ",";
				}
			}
			currentUser.setAgentProduct(agentPros);
		}
		currentUser.setPhone(r.get("phone") == null ? null : r.get("phone").toString());
		currentUser.setEmail(r.get("email") == null ? null : r.get("email").toString());
		currentUser.setLogintime(r.get("logintime") == null ? "" : r.get("logintime").toString());
		currentUser.setRoles((List<String>) (r.get("roles") == null ? null : r.get("roles")));
		currentUser.setAuths((List<String>) (r.get("auths") == null ? null : r.get("auths")));
		currentUser.setHtmlTemp(r.get("htmlTemp") == null ? null : r.get("htmlTemp").toString());
		currentUser.setBindWeChatUrl(r.get("bindWeChatUrl") == null ? null : r.get("bindWeChatUrl").toString());
		currentUser.setAesPassword(r.get("aesPassword") == null ? null : r.get("aesPassword").toString());
		currentUser.setRefQrcodeUrl(r.get("refQrcodeUrl") == null ? null : r.get("refQrcodeUrl").toString());
		currentUser.setIsInit(((int) (Double.parseDouble(r.get("isInit") == null ? "0" : r.get("isInit").toString()))));

		return currentUser;
	}

	/**
	 * 缓存 key 手机 : value 验证码
	 */
	private String cachePhoneCode(final String url, final Pager pager) {
		if (pager.getCode() == 200) {
			final String phone = url.split("/")[1];
			final String phoneCode = pager.getResult().toString();
			if (!StringUtil.isBlank(phone) && !StringUtil.isBlank(phoneCode)) {
				final String phonecode_timeout = propertyUtil.getValue("sys.phonecode_timeout");
				logger.debug("cache phone code ====> phone:" + phone + " code:" + phoneCode + " timeout:" + phonecode_timeout);
				mcm.set(phone, phoneCode, Integer.parseInt(phonecode_timeout));
				return "{\"code\":" + pager.getCode() + "}";
			}
		}
		return null;
	}

	/**
	 * 从缓存中 校验手机验证码
	 *
	 * @param userObj
	 * @return
	 */
	private JSONObject checkPhoneCode(final JSONObject userObj) {
		final JSONObject result = new JSONObject();
		final String phone = userObj.getString("phone");
		final String phoneCode = userObj.getString("phoneCode");
		if (StringUtil.isBlank(phone) || StringUtil.isBlank(phoneCode)) {
			result.put("code", 401);
			result.put("result", "手机或验证码参数错误");
			result.put("message", "手机或验证码参数错误");
		} else {
			final Object cachePhoneCode = mcm.get(phone);
			if ((cachePhoneCode == null)) {
				result.put("code", 401);
				result.put("result", "验证码过期或未发送");
				result.put("message", "验证码过期或未发送");
			} else {
				if (cachePhoneCode.equals(phoneCode)) {
					result.put("code", 200);
				} else {
					result.put("code", 401);
					result.put("result", "验证码错误");
					result.put("message", "验证码错误");
				}
			}
		}
		return result;
	}

	/**
	 * 初始化CSRF(跨站请求伪造)信息
	 *
	 * @return
	 */
	private void initCSRFInfo(final String sessionid, final HttpServletResponse response, final boolean refresh) {
		String csrf_token = null;
		final String prefix = propertyUtil.getValue("sys.csrf_token_prefix");
		if (mcm.get(prefix + sessionid) == null) {
			final int csrf_token_timeout = propertyUtil.getIntValue("sys.csrf_token_timeout");
			csrf_token = CSRFUtil.generateToken();
			mcm.set(prefix + sessionid, csrf_token);
			final Cookie cookie = new Cookie("csrftoken", csrf_token);
			response.addCookie(cookie);
			//mcm.set(prefix + ip, csrf_token, csrf_token_timeout);
		} else {
			csrf_token = mcm.get(prefix + sessionid).toString();
			//重置过期时间
			/*
			 * String csrf_token = mcm.get(prefix + ip).toString(); mcm.set(prefix + ip, csrf_token, csrf_token_timeout);
			 */
		}
		if (refresh) {
			final Cookie cookie = new Cookie("csrftoken", csrf_token);
			response.addCookie(cookie);
		}
	}

	/**
	 * CSRF(跨站请求伪造)校验
	 *
	 * @param request
	 * @param sessionid
	 * @return
	 */
	private JSONObject checkCSRF(final HttpServletRequest request, final String sessionid) {
		final JSONObject result = new JSONObject();
		final String prefix = propertyUtil.getValue("sys.csrf_token_prefix");
		if (mcm.get(prefix + sessionid) != null && request.getCookies() != null) {
			final String csrf_token = mcm.get(prefix + sessionid).toString();
			String csrfmiddlewaretoken = "";
			final Cookie[] cookies = request.getCookies();
			for (final Cookie cookie : cookies) {
				if ("csrftoken".equals(cookie.getName())) {
					csrfmiddlewaretoken = cookie.getValue();
					break;
				}
			}
			if (!StringUtil.isBlank(csrf_token) && !StringUtil.isBlank(csrfmiddlewaretoken)
				&& csrf_token.equals(csrfmiddlewaretoken)) {
				result.put("code", 200);
				return result;
			}
		}
		/*
		 * JSONObject result = new JSONObject(); String prefix = propertyUtil.getValue("sys.csrf_token_prefix");
		 * if(mcm.get(prefix + sessionid) != null && jsonObject != null && jsonObject.getString("csrfmiddlewaretoken") !=
		 * null) { String csrf_token = mcm.get(prefix + sessionid).toString(); String csrfmiddlewaretoken =
		 * jsonObject.getString("csrfmiddlewaretoken"); if(!StringUtil.isBlank(csrf_token) &&
		 * !StringUtil.isBlank(csrfmiddlewaretoken) && csrf_token.equals(csrfmiddlewaretoken)) { result.put("code", 200);
		 * return result; } }
		 */
		// CSRF验证失败 请求终止
		result.put("code", 403);
		result.put("result", "CSRF verification failed. Request aborted");
		result.put("message", "CSRF verification failed. Request aborted");
		return result;
	}

}
