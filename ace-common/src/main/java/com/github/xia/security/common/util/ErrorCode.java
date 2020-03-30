package com.github.xia.security.common.util;

import lombok.Getter;

/**
 * 通用异常类错误代码
 * 
 * @author XIA
 * @date 2020年1月15日
 * @version 1.0
 * @since JDK：1.8
 */
public enum ErrorCode {

	SUCCESS("0000", "操作成功"),
	
	CHECK_ERR("0001", "检测错误"),

	DATA_ERR("0002", "数据异常"),

	SQL_ERR("0003", "系统异常，请联系管理员"),

	PERMISSION_ERR("0004", "无权限"),
	
	CALL_FEIGN_ERROR("0005", "调用feign 服务异常"),

	WORKFLOW_ERROR("0006", "调用流程服务异常"),
	
	UNDEFINED_ERR("1000", "未定义异常");

	/**
	 * 错误编码
	 */
	@Getter
	private String errCode;

	/**
	 * 错误编码的信息
	 */
	@Getter
	private String errMessage;

	ErrorCode(String errCode, String errMessage) {
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

}
