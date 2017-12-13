/*
################################################################################
#                                                                               
# Name : UserData.java       
# Author: Zhou Tao                                             
# Desc : UserData is used to load login session information.
#                                                                               
#                                                                               
# (C) COPYRIGHT IBM Corporation 2013                                      
# All Rights Reserved.                                                          
#                                                                               
# Licensed Materials-Property of IBM                                            
#                                                                               
################################################################################ 
*/
package org.marker.mushroom.dto;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/** @noinspection unused */
public class UserData implements Serializable {

	private int userId;

	private String language;

	private String account;

	private Long accountid = 1L;

	private String userName;

	private String surName;

	private String directoryKey;

	private String phone;

	private String email;

	private int isActived;

	private int userType;

	private String logintime;

	private int companyId;

	private int agentId;

	private String agentProduct;

	private String mobile;

	private String department;

	private String status;

	private int accountStatus = 0;

	private String companyLogo;
	private String abbreviationName;

	private String bindWeChatUrl;

	private String aesPassword;
	/**
	 * vipLevel:VIP等级		0:非VIP		101：普通会员		201:黄金会员		301:铂金会员
	 */
	private int vipLevel;
	/**
	 * 推广二维码
	 */
	private String refQrcodeUrl;
	/**
	 * 推荐人推广码
	 */
	private String referrerCode;

	private List<String> roles;
	private List<String> auths;

	/**
	 * 1: agent admin
	 * 2: account admin
	 * 9X:
	 * 91: platform admin
	 * 92: account admin
	 * 93: network admin
	 * 94: operation admin
	 * 95: system admin
	 */
	private String role;

	private String createDate;

	private int timeZone;

	private boolean isSystemManager = false;

	private boolean isAccountManager = false;

	private boolean isAgentManager = false;

	private String htmlTemp;

	private int isInit;

	private int adminId;

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getDirectoryKey() {
		return directoryKey;
	}

	public void setDirectoryKey(String directoryKey) {
		this.directoryKey = directoryKey;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getTimeZone() {
		return timeZone;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

	public Long getAccountid() {
		return accountid;
	}

	public void setAccountid(Long accountid) {
		this.accountid = accountid;
	}

	public boolean getSystemManager() {
		return isSystemManager;
	}

	public void setSystemManager(boolean isSystemManager) {
		this.isSystemManager = isSystemManager;
	}

	public boolean getAccountManager() {
		return isAccountManager;
	}

	public void setAccountManager(boolean isAccountManager) {
		this.isAccountManager = isAccountManager;
	}

	public boolean getAgentManager() {
		return isAgentManager;
	}

	public void setAgentManager(boolean isAgentManager) {
		this.isAgentManager = isAgentManager;
	}

	public UserData() {
		super();
	}

	public String getAbbreviationName() {
		return abbreviationName;
	}

	public void setAbbreviationName(String abbreviationName) {
		this.abbreviationName = abbreviationName;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getIsActived() {
		return isActived;
	}

	public void setIsActived(int isActived) {
		this.isActived = isActived;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public boolean isSystemManager() {
		return isSystemManager;
	}

	public boolean isAccountManager() {
		return isAccountManager;
	}

	public boolean isAgentManager() {
		return isAgentManager;
	}

	public UserData(int userId, String language, String account, Long accountid, String userName, String surName,
					String directoryKey, String phone, int isActived, int userType, String logintime, int companyId,
					String mobile, String department, String status, int accountStatus, String role, String createDate,
					int timeZone, boolean isSystemManager, boolean isAccountManager, boolean isAgentManager) {
		super();
		this.userId = userId;
		this.language = language;
		this.account = account;
		this.accountid = accountid;
		this.userName = userName;
		this.surName = surName;
		this.directoryKey = directoryKey;
		this.phone = phone;
		this.isActived = isActived;
		this.userType = userType;
		this.logintime = logintime;
		this.companyId = companyId;
		this.mobile = mobile;
		this.department = department;
		this.status = status;
		this.accountStatus = accountStatus;
		this.role = role;
		this.createDate = createDate;
		this.timeZone = timeZone;
		this.isSystemManager = isSystemManager;
		this.isAccountManager = isAccountManager;
		this.isAgentManager = isAgentManager;
	}

	public String toString() {
		final StringBuilder s = new StringBuilder();
		Field[] fds = this.getClass().getDeclaredFields();

		Arrays.stream(fds)
			  .filter(f -> f.getModifiers() == 17 || f.getModifiers() == 1)
			  .forEach(f -> {
				  String fieldName = f.getName();
				  Object fieldValue;
				  try {
					  fieldValue = f.get(this);
					  if (fieldValue instanceof String[]) {
						  String[] tmp = (String[]) fieldValue;
						  fieldValue = "[" + Arrays.stream(tmp)
												   .map(str -> "\"" + str + "\"")
												   .reduce((x, y) -> x + "," + y)
												   .orElse("") + "]";
//						  String tmpStr = "[";
//						  int size = tmp.length;
//						  for (int j = 0; j < size; ++j) {
//							  if (j < size - 1) {
//								  tmpStr += "\"" + tmp[j] + "\",";
//							  } else {
//								  tmpStr += "\"" + tmp[j] + "\"]";
//							  }
//						  }
//						  fieldValue = tmpStr;
					  }
					  s.append(fieldName).append(" = ").append(fieldValue).append("\n");
				  } catch (IllegalArgumentException | IllegalAccessException e) {
					  e.printStackTrace();
				  }
			  });
		return s.toString();
	}

	public String getAgentProduct() {
		return agentProduct;
	}

	public void setAgentProduct(String agentProduct) {
		this.agentProduct = agentProduct;
	}

	public void setAccountStatus(int accountStatus) {
		this.accountStatus = accountStatus;
	}

	public int getAccountStatus() {
		return accountStatus;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getAuths() {
		return auths;
	}

	public void setAuths(List<String> auths) {
		this.auths = auths;
	}

	public String getHtmlTemp() {
		return htmlTemp;
	}

	public void setHtmlTemp(String htmlTemp) {
		this.htmlTemp = htmlTemp;
	}

	public String getBindWeChatUrl() {
		return bindWeChatUrl;
	}

	public void setBindWeChatUrl(String bindWeChatUrl) {
		this.bindWeChatUrl = bindWeChatUrl;
	}

	public String getAesPassword() {
		return aesPassword;
	}

	public void setAesPassword(String aesPassword) {
		this.aesPassword = aesPassword;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public String getRefQrcodeUrl() {
		return refQrcodeUrl;
	}

	public void setRefQrcodeUrl(String refQrcodeUrl) {
		this.refQrcodeUrl = refQrcodeUrl;
	}

	public String getReferrerCode() {
		return referrerCode;
	}

	public void setReferrerCode(int userId) {
		String referrerCode = "rc";
		if (userId > 0) {
			String tempUserId = userId + "";
			if (tempUserId.length() < 4) {
				for (int i = 0; i < 4 - tempUserId.length(); i++) {
					referrerCode += 0;
				}
				referrerCode += tempUserId;
			} else {
				//referrerCode = referrerCode.substring(0, 6-tempUserId.length());
				referrerCode += tempUserId;
			}
		} else {
			referrerCode = "";
		}
		this.referrerCode = referrerCode;
	}

	public int getIsInit() {
		return isInit;
	}

	public void setIsInit(int isInit) {
		this.isInit = isInit;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

}
