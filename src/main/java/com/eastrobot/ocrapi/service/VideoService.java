/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.service;
/**
 * <pre>
 * 视频识别文本
 * 1、将视频切割成图片，调用 ImageService 方法进行图片中文本识别
 * 2、将视频提取音频，调用 asr 接口进行文本识别
 * 3、合并 1、 2 两步骤中返回的文本
 * </pre>
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年2月2日 上午10:31:02
 * @version 1.0
 */
public interface VideoService {

	/**
	 * 处理视频文件，识别文本
	 * @author eko.zhan at 2018年2月2日 上午10:42:05
	 * @param inputPath
	 * @return
	 */
	public String handle(String inputPath);
}
