package org.marker.ext;

import org.marker.ext.groovy.GroovyScriptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模型工具
 *
 * @author marker
 * @version 1.0
 */
public class ModuleUtils {

	protected final Logger log = LoggerFactory.getLogger(ModuleUtils.class);

	protected GroovyScriptUtil util;

	public void setUtil(GroovyScriptUtil util) {
		this.util = util;
	}

}
