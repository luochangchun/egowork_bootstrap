package org.marker.mushroom.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	public static boolean isBlank(String str) {
		return str == null || "".equals(str) || "null".equals(str);
	}

	public static boolean isBlankStr(String str) {
		return isBlank(str);
//		if(StringUtils.isBlank(str)){
//			return true;
//		}else if(str.trim().toLowerCase().equals("null")){
//			return true;
//		}
//		return false;
	}

	/**
	 * 通过资产的url获取用户资产的域名(OA、CRM)
	 *
	 * @param url
	 * @return
	 */
	public static String getDomanUrl(String url) {
		if (!isBlank(url)) {
			Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|io)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(url);
			matcher.find();
			String domainUrl = matcher.group() + "/";
			//域名后面的后缀
			String suffix = url.substring(url.indexOf(domainUrl) + domainUrl.length());
			String identifyStr = suffix.substring(0, suffix.indexOf("/"));
			return "http://" + domainUrl + identifyStr;
		}
		return null;
	}

	/**
	 * 通过资产的url获取用户资产的域名(Handisk)
	 *
	 * @param url
	 * @return
	 */
	public static String getDomanUrlByHandisk(String url) {
		if (!isBlank(url)) {
			Pattern p = Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv|io)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = p.matcher(url);
			matcher.find();
			String domainUrl = matcher.group();
			return "https://" + domainUrl;
			//return "http://" + domainUrl;
		}
		return null;
	}

	public static String join(String[] str, String ins) {
		return Arrays.stream(str).reduce((x, y) -> x + ins + y).orElse("");
//		String ret = "";
//		for (String val : str) {
//			ret += val + ins;
//		}
//		return ret.lastIndexOf(ins) >= 0 ? ret.substring(0, ret.lastIndexOf(ins)) : "";
	}
}
