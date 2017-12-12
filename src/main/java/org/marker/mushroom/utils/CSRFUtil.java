package org.marker.mushroom.utils;

public class CSRFUtil {

	public static String generateToken() {
		MD5 md5 = new MD5();
		return md5.getMD5ofStr(UUIDGenerator.getPureUUID());
	}
	
}
