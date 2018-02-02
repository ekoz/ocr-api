/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.web;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年10月28日 上午11:53:28
 * @version 1.0
 */
public class BaseController {
	
	protected final String UTF8 = "UTF-8";

	protected JSONObject writeSuccess() {
		return writeSuccess("");
	}
	
	protected JSONObject writeSuccess(String message) {
		JSONObject json = new JSONObject();
		json.put("result", 1);
		json.put("message", message);
		return json;
	}
	
	protected JSONObject writeFailure() {
		return writeFailure("");
	}
	
	protected JSONObject writeFailure(String message) {
		JSONObject json = new JSONObject();
		json.put("result", 0);
		json.put("message", message);
		return json;
	}
}
