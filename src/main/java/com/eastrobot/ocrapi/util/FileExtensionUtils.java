/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月21日 下午3:10:34
 * @version 1.0
 */
public class FileExtensionUtils {

	private static String[] videoArray = new String[]{"mp4", "avi", "wma", "rmvb", "rm", "flash", "mid", "3gp"};
	private static String[] picArray = new String[]{"jpg", "jpeg", "png", "gif", "ico", "bmp"};
	
	/**
	 * 是否是视频文件
	 * @author eko.zhan at 2017年12月21日 下午3:38:28
	 * @return
	 */
	public static Boolean isVideo(String filename){
		String extension = FilenameUtils.getExtension(filename).toLowerCase();
		if (ArrayUtils.contains(videoArray, extension)){
			return true;
		}
		return false;
	}
	/**
	 * 是否是图片
	 * @author eko.zhan at 2017年12月21日 下午3:47:18
	 * @param filename
	 * @return
	 */
	public static Boolean isImage(String filename){
		String extension = FilenameUtils.getExtension(filename).toLowerCase();
		if (ArrayUtils.contains(picArray, extension)){
			return true;
		}
		return false;
	}
	
}
