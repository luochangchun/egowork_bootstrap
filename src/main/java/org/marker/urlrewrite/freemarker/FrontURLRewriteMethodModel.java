package org.marker.urlrewrite.freemarker;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.urlrewrite.URLRewriteEngine;

import java.util.List;

/**
 * URL重写标签(for freemarker) ${encoder("/cms?p=index")} 前端URL重写方法模型
 * 
 * @author marker
 * */
public final class FrontURLRewriteMethodModel implements TemplateMethodModel {

	private final URLRewriteEngine urlrewrite = SingletonProxyFrontURLRewrite.getInstance();

	/**
	 * URL重写处理方法
	 */
	public Object exec(List args) throws TemplateModelException {
		String fakeUrl = "";
		if (args != null && args.size() > 0) {
			String realUrl = (String) args.get(0);
			fakeUrl = urlrewrite.encoder(urlrewrite.getUrlPattern()+realUrl); 
		}
		return fakeUrl;
	}

}
