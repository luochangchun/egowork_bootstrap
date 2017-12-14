package org.marker.mushroom.utils;

import org.marker.mushroom.core.config.impl.SystemConfig;
import org.marker.security.DES;
import org.marker.security.MD5;

import java.util.Base64;

/**
 * 密码加密生成器
 * 
 * @author marker
 * */
public class GeneratePass {

	private static final SystemConfig syscfg = SystemConfig.getInstance();
	
	public static String encode(String password) throws Exception {
		String key = syscfg.get("secret_key");
		 return MD5.getMD5Code(Base64.getEncoder().encodeToString(DES.encrypt(password.getBytes(), key)));
	}

}
