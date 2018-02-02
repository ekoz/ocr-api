/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月21日 下午3:52:12
 * @version 1.0
 */
public interface OcrService {

	/**
	 * 传入文件路径，然后发车
	 * @author eko.zhan at 2018年2月2日 上午10:33:41
	 * @param inputPath
	 * @return 文件中的文本
	 */
	public JSONObject drive(String inputPath);
	/**
	 * 获取当天时间生成的文件夹
	 * @author eko.zhan at 2017年12月21日 下午4:03:14
	 * @return
	 */
	public String getFolderString();
}
