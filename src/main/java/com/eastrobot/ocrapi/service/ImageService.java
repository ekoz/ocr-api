/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.service;
/**
 * <pre>
 * 图像识别文本
 * 采用 Tesseract-ocr 进行文本提取
 * </pre>
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年2月2日 上午10:30:37
 * @version 1.0
 */
public interface ImageService {

	/**
	 * 处理图片文件，识别文本
	 * @author eko.zhan at 2018年2月2日 上午10:42:18
	 * @param inputPath
	 * @return
	 */
	public String handle(String inputPath);
}
