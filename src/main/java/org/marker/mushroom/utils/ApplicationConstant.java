package org.marker.mushroom.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicationConstant {
	
	/*新闻展示条数*/
	public static final int CHANNEL_SHOW_NUMBERS  = 20;
	
	/*新闻展示条数*/
	public static final int ARTICLE_SHOW_NUMBERS  = 5;
	
	/*控制台模板展示条数*/
	public static final int TEMPLATE_SHOW_NUMBERS  = 5;
	
	/*分页展示条数*/
	public static final int LIST_PAGING_SHOW_NUMBERS  = 20;
	
	/*列表每页展示数*/
	public static final int LIST_PAGESIZE  = 10;
	
	/*企业云库展示数*/
	public static final int LIST_ENTERPRISE  = 9;
	
	/*列出所属行业条数*/
	public static final int LIST_ARTICLE_INDUSTRY = 6;

	/*列出所属分类条数*/
	public static final int LIST_ARTICLE_CLASSIFY = 6;
	
	/*产品模块分页条数*/
	public static final int LIST_PRODUCT_PAGESIXE = 10;
	
	public static final String HANSAP_IDENTIFIER = "whicloud";
	//单一的资产类型
	public static final List<Integer> ASSET_SIMPLE_ARR = new ArrayList<Integer>(Arrays.asList(1004, 1008, 1016));
	//用户注册成功,免费服务(产品)自动开通资产
	//public static final List<Integer> REGIETER_AUTO_ASSET = new ArrayList<Integer>(Arrays.asList(1004, 1005, 1010, 1024));
	//用户注册成功,免费服务(产品)自动开通时长
	public static final int REGIETER_AUTO_ASSET_BUYLONG = 10;
	//用户注册成功,免费服务(产品)自动开通数量
	public static final int REGIETER_AUTO_ASSET_BUYNUMBER = 100;
	//用户注册成功,免费服务(产品)自动开通时长的单位  1:年  2:月
	public static final int REGIETER_AUTO_ASSET_BUYLONG_UNIT = 1;
	
	/**邮箱正则表达式*/
    //public static final String emailRegex="^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
	public static final String emailRegex="[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    /**手机号码正则表达式*/
    public static final String phoneRegex="^1[3|4|5|7|8][0-9]\\d{8}$";
    /**后台用户登录名正则表达式*/
    public static final String loginNameRegex="^[a-zA-Z]{1}[0-9a-zA-Z_]{5,22}$";
    /**用户名正则表达式*/
    public static final String userNameRegex = "^[a-zA-Z]{1}[0-9a-zA-Z_]{5,22}$";
    /**密码正则表达式*/
    public static final String passwordRegex = "^[A-Za-z0-9\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\`\\~\\-\\=]{6,22}$";
    
	/** 文件上传请求路径 */
//    public static final String UPLOAD_REQ_PATH = "https://localhost.hansap.com:8443/wisme-img/upload?attributeName=";
  public static final String UPLOAD_REQ_PATH = "https://image.hansap.com/wisme-img/upload?attributeName=";
//  public static final String UPLOAD_REQ_PATH = "https://192.168.11.209:8443/wisme-img/upload?attributeName=";
//  public static final String UPLOAD_REQ_PATH = "https://test2016.hansap.com/wisme-img/upload?attributeName=";

  	/**
  	 *  用户缓存key后缀
  	 */
  	public static final String USER_CACHE_SUFFIX = "_USERDATA";
}
