package org.marker.mushroom.ext.plugin.freemarker;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.marker.mushroom.ext.plugin.PluginContext;

import java.util.List;

/**
 * 嵌入式指令调用标签(for freemarker)
 *
 * @author marker
 */
// TODO check side effects on TemplateMethodModelEx
public class EmbedDirectiveInvokeTag implements TemplateMethodModelEx {

	@Override
	public Object exec(List args) throws TemplateModelException {
		String errorStr = "<!-- 插件调用错误！ -->";
		if (args != null && args.size() > 1) {
			String pluginName = (String) args.get(0);
			String directive = (String) args.get(1);

			// 获取插件作用域
			PluginContext pluginContext = PluginContext.getInstance();
			try {
				return pluginContext.invoke(pluginName, directive);
			} catch (Exception e) {
				return errorStr + " Because Of Exception";
			}
		}
		return errorStr;
	}

}
