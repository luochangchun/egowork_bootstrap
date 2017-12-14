
package org.marker.mushroom.core.exception;

/**
 * 模板文件未找到异常
 *
 * @author marker
 * @version 1.0
 * @date 2013-8-24 下午12:42:17
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public class TemplateFileNotFoundException extends SystemException {

	private static final long serialVersionUID = -8469032613028009433L;

	public TemplateFileNotFoundException() {
		super("模板文件未找到");
	}

	/**
	 * @param tplFileName
	 * @param childTemplateFileName
	 */
	public TemplateFileNotFoundException(String tplFileName,
										 String childTemplateFileName) {
		super("模板文件 \"" + tplFileName + "\" 中的引用\"" + childTemplateFileName + "\"文件未找到!");
	}

	/**
	 * @param tplFileName
	 */
	public TemplateFileNotFoundException(String tplFileName) {
		super("模板文件 \"" + tplFileName + "\" 未找到!");
	}

}
