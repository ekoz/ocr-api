/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.config;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月21日 下午1:56:40
 * @version 1.0
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
	
	//全局异常处理
	@ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception e, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
	
	//预处理前台请求参数
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
		
    }
}
