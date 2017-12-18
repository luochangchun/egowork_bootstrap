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
	private Object param;

	public ResultMessage(final String message) {
		this.status = false;
		this.message = message;
		this.param = null;
	}

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
	public ResultMessage(final boolean status, final String message, final Object param) {
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

	public Object getParam() {
		return param;
	}

	public void setParam(final Object param) {
		this.param = param;
	}
}
