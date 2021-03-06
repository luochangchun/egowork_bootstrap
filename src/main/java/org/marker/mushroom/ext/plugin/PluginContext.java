package org.marker.mushroom.ext.plugin;

import com.google.gson.Gson;
import freemarker.template.Template;
import org.marker.mushroom.context.ActionContext;
import org.marker.mushroom.ext.plugin.freemarker.EmbedDirectiveInvokeTag;
import org.marker.mushroom.freemarker.LoadDirective;
import org.marker.mushroom.freemarker.UpperDirective;
import org.marker.urlrewrite.freemarker.FrontURLRewriteMethodModel;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 插件容器，主要存放插件
 *
 * @author marker
 * @version 1.0
 */
public class PluginContext {

	// 存放HTTP插件
	// 插件路径/代理 ，主要是因为这块的热部署功能，因此使用并发库中线程安全HanMap。
	private static final Map<String, ProxyPluginlet> pluginLets = new ConcurrentHashMap<>();

	private PluginContext() { }

	/**
	 * 这种写法最大的美在于，完全使用了Java虚拟机的机制进行同步保证。
	 */
	private static class SingletonHolder {

		public final static PluginContext instance = new PluginContext();
	}

	/**
	 * 获取数据库配置实例
	 */
	public static PluginContext getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 添加分发器
	 *
	 * @param pluginlet Pluginlet
	 * @throws Exception
	 */
	public void put(Pluginlet pluginlet) throws Exception {

		String type = pluginlet._config.get(Pluginlet.CONFIG_TYPE).toString();

		pluginLets.put(type, new ProxyPluginlet(pluginlet));

		// 数据库持久化
	}

	/**
	 * 移除分发器
	 *
	 * @param type String
	 * @throws Exception
	 */
	public void remove(String type) throws Exception {
		pluginLets.remove(type);
		// 数据库同步
	}

	/**
	 * 调用
	 *
	 * @param httpMethod String
	 * @param uri String
	 * @return ViewObject
	 * @throws Exception
	 */                      // POST              /guestbook/add
	public ViewObject invoke(String httpMethod, String uri) throws Exception {
		if (httpMethod == null) throw new Exception("request method invalid");
		int index = uri.indexOf("/");
		if (index != -1) {// 解析成功
			String pluginName = uri.substring(0, index);// 插件名称
			String pluginCurl = uri.substring(index, uri.length());// 插件功能URL 
			ProxyPluginlet pluginProxy = pluginLets.get(pluginName);
			if (pluginProxy != null) {
				ViewObject view = pluginProxy.invoke(httpMethod, pluginCurl);
				if (view != null) {// 如果返回值为空，代表是自己手动处理
					Writer out = ActionContext.getResp().getWriter();
					switch (view.getType()) {
						case JSON:
							String outStr = new Gson().toJson(view.getResult());
							out.write(outStr);
//							JSON.writeJSONStringTo(view.getResult(), out, SerializerFeature.WriteClassName);
							break;
						case HTML:
							String path = "views" + File.separator + view.getResult();

							Template template = pluginProxy.getTemplate(path);

							ServletContext application = ActionContext.getApplication();
							HttpServletRequest request = ActionContext.getReq();

							Map<String, Object> root = new HashMap<>();

							root.put("encoder", new FrontURLRewriteMethodModel());//URL重写
							root.put("list", new UpperDirective());// 调用
							root.put("load", new LoadDirective());//
							root.put("plugin", new EmbedDirectiveInvokeTag());// 嵌入式指令插件
							Enumeration<String> attrs3 = application.getAttributeNames();
							while (attrs3.hasMoreElements()) {
								String attrName = attrs3.nextElement();
								root.put(attrName, application.getAttribute(attrName));
							}
							//转移Session数据
							HttpSession session = request.getSession();
							Enumeration<String> attrs2 = session.getAttributeNames();
							while (attrs2.hasMoreElements()) {
								String attrName = attrs2.nextElement();
								root.put(attrName, session.getAttribute(attrName));
							}
							//这里是进行数据转移
							Enumeration<String> attrs = request.getAttributeNames();
							while (attrs.hasMoreElements()) {
								String attrName = attrs.nextElement();
								root.put(attrName, request.getAttribute(attrName));
							}

							template.process(root, out);
							break;
						default:
							break;
					}
					out.flush();
					out.close();
				}
			}
		}
		return null;
	}

	/**
	 * 获取当前维护的Pluginlet代理
	 *
	 * @return
	 * @noinspection unused
	 */
	public Map<String, ProxyPluginlet> getPluginLet() {
		return pluginLets;
	}

	public List<Object> getList() {
		return pluginLets.keySet()
						 .stream()
						 .map(pluginLets::get)
						 .map(ProxyPluginlet::getConfig)
						 .collect(Collectors.toList());
	}

}
