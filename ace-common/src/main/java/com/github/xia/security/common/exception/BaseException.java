package com.github.xia.security.common.exception;

import lombok.Getter;

import java.io.Serializable;

/**
 * 自定异常，系统抛出异常【自定义的异常】
 * 
 * @author XIA
 * @date 2020年1月15日
 * @version 1.0
 * @since JDK：1.8
 */
public class BaseException extends Exception implements Serializable {

	private static final long serialVersionUID = 1290703878323919730L;

	/**
	 * 错误代码
	 */
	@Getter
	private final String errCode;

	/**
	 * 自定义异常信息
	 */
	@Getter
	private final String customMess;

	/**
	 * 只给出异常信息，异常编码为空
	 *
	 * @param customMess 自定义异常信息
	 */
	public BaseException(String customMess) {
		super(customMess);
		this.errCode = "";
		this.customMess = customMess;
	}

	/**
	 * 只给出异常信息，异常编码为空
	 *
	 * @param customMess 自定义异常信息
	 * @param t          堆栈异常对象
	 */
	public BaseException(String customMess, Throwable t) {
		super(customMess, t);
		this.errCode = "";
		this.customMess = customMess;
	}

	/**
	 *
	 * @param errCode    异常编码，详见：{@link ErrorCode}
	 * @param customMess 自定义异常信息
	 * @param t          堆栈异常对象
	 */
	public BaseException(String errCode, String customMess, Throwable t) {
		super(customMess, t);
		this.errCode = errCode;
		this.customMess = customMess;
	}


	/**
	 *
	 * @param errCode    异常编码，详见：{@link ErrorCode}
	 * @param customMess 自定义异常信息
	 */
	public BaseException(String errCode, String customMess) {
		super(customMess);
		this.errCode = errCode;
		this.customMess = customMess;
	}

}
