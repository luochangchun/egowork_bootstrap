package org.marker.mushroom.ext.plugin.freemarker;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.marker.mushroom.ext.plugin.PluginContext;

import java.util.List;

/**
 * 嵌入式指令调用标签(for freemarker)
 *
 * @author marker
 */
public class EmbedDirectiveInvokeTag implements TemplateMethodModelEx {

	@Override
	public Object exec(List args) throws TemplateModelException {
		String errorStr = "<!-- 插件调用错误！ -->";
		if (args != null && args.size() > 1) {

			String pluginName = ((SimpleScalar) args.get(0)).getAsString();
			String directive = ((SimpleScalar) args.get(1)).getAsString();

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
