package org.marker.mushroom.component;

import freemarker.template.Configuration;
import freemarker.template.Version;
import org.marker.mushroom.ext.plugin.freemarker.EmbedDirectiveInvokeTag;
import org.marker.mushroom.freemarker.BootStrap3NavDirective;
import org.marker.mushroom.freemarker.LoadDirective;
import org.marker.mushroom.template.NewStringTemplateLoader;
import org.marker.urlrewrite.freemarker.FrontURLRewriteMethodModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * freemarker模板引擎配置
 *
 * @author marker
 * @version 1.0
 * @Component(Core.CONFIG_FREEMARKER)
 */
public class FreeMarkerConfigurer {

	/** 日志记录对象 */
	protected Logger logger = LoggerFactory.getLogger(FreeMarkerConfigurer.class);

	// freemarker配置
	private static final Configuration config = new Configuration(new Version("2.3.23"));

	// 编码集(默认UTF-8)
	public static final String encoding = "utf-8";

	// 本地语言(默认汉语)
	public static final Locale locale = Locale.CHINA;

	// 模板加载器
	private final NewStringTemplateLoader loader = new NewStringTemplateLoader();

	/**
	 * freemarker配置对象
	 */
	public FreeMarkerConfigurer() {
		logger.info(">>>>> Freemarker Configuration Initing");
		config.setDefaultEncoding(encoding);
		config.setOutputEncoding(encoding);
		config.setLocale(locale);
		config.setLocalizedLookup(false);
//        config.setTemplateLoader(loader);//设置模板加载器

		// 初始化分享变量
		initSharedVariable();
	}

	public static void initSharedVariable() {
		config.setSharedVariable("load", new LoadDirective());
		config.setSharedVariable("Boostrap3Nav", new BootStrap3NavDirective());// 导航菜单
		config.setSharedVariable("encoder", new FrontURLRewriteMethodModel());//URL重写
		config.setSharedVariable("plugin", new EmbedDirectiveInvokeTag());// 嵌入式指令插件
	}

	public Configuration getConfig() {
		return config;
	}

	public NewStringTemplateLoader getLoader() {
		return loader;
	}
}

