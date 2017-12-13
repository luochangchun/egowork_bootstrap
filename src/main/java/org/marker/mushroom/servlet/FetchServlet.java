/**
 * 吴伟 版权所有
 */
package org.marker.mushroom.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.marker.mushroom.alias.DAO;
import org.marker.mushroom.beans.Three_visit_log;
import org.marker.mushroom.core.config.impl.DataBaseConfig;
import org.marker.mushroom.dao.ISupportDao;
import org.marker.mushroom.dto.UserData;
import org.marker.mushroom.holder.SpringContextHolder;
import org.marker.mushroom.utils.HttpUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 统计访问数据接口
 *
 * @author marker
 * @date 2013-12-2 下午8:02:53
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class FetchServlet extends HttpServlet {

	private static final long serialVersionUID = 2990324920926049103L;

	private static final List<String> list = Collections.synchronizedList(new ArrayList<String>(30));

	private final DataBaseConfig dbconfig = DataBaseConfig.getInstance();

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

		//		String baseURL = req.getAttribute(AppStatic.WEB_APP_URL).toString();

		final String leave = req.getParameter("leave");// 是否离开

		if (!"1".equals(leave)) {
			final String url = req.getParameter("v0");// 当前url
			final String referer = req.getParameter("v1");// 旧URL
			final String system = req.getParameter("v2");// 操作系统
			final String screen = req.getParameter("v3");// 屏幕分辨率
			final String browser = req.getParameter("v4");// 浏览器
			final String bVersion = req.getParameter("v5");// 浏览器
			final String language = req.getParameter("v6");// 浏览器语言
			final String isFlash = req.getParameter("v7");// 是否安装Flash
			final String ipv4 = HttpUtils.getRemoteHost(req);// 用户真实IP，处理了ngnix的代理IP
			final String visitorId = HttpUtils.getCookie(req, "FETCHSESSIONID");// UV统计使用

			final StringBuilder data = new StringBuilder();
			data.append(ipv4).append(",");
			data.append(language).append(",");
			data.append(browser).append(",");
			data.append(screen).append(",");
			data.append(url).append(",");
			data.append(referer).append(",");
			data.append(bVersion).append(",");
			data.append(system).append(",");
			data.append(visitorId).append(",");
			data.append(isFlash).append(",");

			final String prefix = dbconfig.getPrefix();
			final ISupportDao dao = SpringContextHolder.getBean(DAO.COMMON);

			if (list.size() < 5 - 1) {
				list.add(data.toString());
			} else {// 满了，就批量推入数据库
				list.add(data.toString());
				final List<Object[]> params = list.stream().map(f -> f.split(",")).collect(Collectors.toList());

				String sql = "insert into " + prefix + "visited_his" +
					"(`ip`,`language`,`browser`,`screen`,`url`,`referer`, `version`,`system`,`time`,`visitor`,`flash`)" +
					" values(?,?,?,?,?,?,?,?,sysdate(),?,?)";

				dao.batchUpdate(sql, params);
				list.clear();
			}

			if (url != null && url.contains("#")) {
				final String openUrl = url.split("#")[1];
				if (openUrl != null && openUrl.contains("http")) {
					final String currentUser = (String) req.getSession().getAttribute("currentUser");

					Type t = new TypeToken<Map<String, Object>>(){}.getType();
					final Map<String, Object> map = new Gson().fromJson(currentUser, t);
//					final Map<String, Object> map = JSONObject.parseObject(currentUser);
					if (map != null && map.size() > 0) {
						final UserData userData = new UserData();
						final Map<String, Object> resultMap = map.get("result") == null ? new HashMap<>()
							: new Gson().fromJson(map.get("result").toString(), t);
//							: JSONObject.parseObject(map.get("result").toString());

						userData.setUserName(resultMap.get("userName") == null ? "" : resultMap.get("userName").toString());
						userData.setUserId(resultMap.get("userId") == null ? -1 : (int) resultMap.get("userId"));
						final Three_visit_log three_visit_log = new Three_visit_log();
						three_visit_log.setName(userData.getUserName());
						three_visit_log.setUrl(openUrl);
						three_visit_log.setTime(new Date());
						three_visit_log.setUserId(userData.getUserId());
						dao.save(three_visit_log);
					}
				}
			}
		} else {// 离开
			System.out.println("用户已经离开");
		}

	}
}
