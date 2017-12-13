/**
 * 吴伟 版权所有
 */
package org.marker.mushroom.ext.model;

import org.marker.mushroom.core.WebParam;
import org.marker.mushroom.core.exception.SystemException;
import org.marker.mushroom.template.tags.res.SqlDataSource;

/**
 * 模型驱动引擎
 *
 * @author marker
 * @date 2013-9-18 上午11:37:02
 * @version 1.0
 * @blog www.yl-blog.com
 * @weibo http://t.qq.com/wuweiit
 */
public interface IContentModelParse {

	int INSTALL_FAILD = 0x00;
	// 已经存在
	int INSTALL_EXIST = 0x01;

	int INSTALL_SUCCESS = 0x02;

	/** 解析错误 */
	int STATUS_ERROR = 0;

	/** 重定向 */
	int STATUS_REDIRECT = 1;

	/** 内容模型 */
	int STATUS_MODULE = 2;
	/** 内容模型未找到 */
	int STATUS_NOT_FOUND_MODULE = 3;

	/**
	 * 通过解析对象找到对应的模型
	 * @param param WebParam
	 * @return int
	 * @throws SystemException
	 */
	int parse(WebParam param) throws SystemException;

	StringBuilder parse(String tableName, SqlDataSource sqldatasource) throws SystemException;

}
