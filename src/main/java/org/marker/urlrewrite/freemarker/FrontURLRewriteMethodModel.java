package org.marker.urlrewrite.freemarker;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.marker.mushroom.core.proxy.SingletonProxyFrontURLRewrite;
import org.marker.urlrewrite.URLRewriteEngine;

import java.util.List;

/**
 * URL重写标签(for freemarker) ${encoder("/cms?p=index")} 前端URL重写方法模型
 * 
 * @author marker
 * */
public final class FrontURLRewriteMethodModel implements TemplateMethodModelEx {

	private final URLRewriteEngine urlrewrite = SingletonProxyFrontURLRewrite.getInstance();

	/**
	 * URL重写处理方法
	 */
	public Object exec(List args) throws TemplateModelException {
		String fakeUrl = "";
		if (args != null && args.size() > 0) {
			SimpleScalar s = (SimpleScalar) args.get(0);
			String realUrl = s.getAsString();
			fakeUrl = urlrewrite.encoder(urlrewrite.getUrlPattern()+realUrl); 
		}
		return fakeUrl;
	}

}
