package org.marker.mushroom.beans;

/**
 * 登录消息
 *
 * @author marker
 */
public class ResultMessage {

	//状态
	private boolean status;
	//消息
	private String message;
	// 传值
	private String param;

	/**
	 * 登录消息
	 *
	 * @param status boolean
	 * @param message String
	 */
	public ResultMessage(final boolean status, final String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * 返回消息
	 *
	 * @param status
	 * @param message
	 * @param param
	 */
	public ResultMessage(final boolean status, final String message, final String param) {
		this.status = status;
		this.message = message;
		this.param = param;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getParam() {
		return param;
	}

	public void setParam(final String param) {
		this.param = param;
	}
}
