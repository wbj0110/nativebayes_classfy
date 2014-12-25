/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu   
 */
package com.soledede.classfy.bayes.framework;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Title:
 * @Description:
 * @Author:wengbenjue
 * @Since:2014年6月27日
 * @Version:1.1.0
 */
@ControllerAdvice
public class ErrorHandler {
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse errorResponse(Exception exception) {
		return new ErrorResponse(exception.getMessage());
	}
}
