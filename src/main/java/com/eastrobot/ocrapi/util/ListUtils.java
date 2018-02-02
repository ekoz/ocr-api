/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.util;

import java.util.Collection;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月26日 下午1:35:43
 * @version 1.0
 */
public class ListUtils {

	/**
	 * 将数组转换成字符串
	 * @author eko.zhan at 2017年12月21日 下午1:18:33
	 * @param list
	 * @return
	 */
	public static String toString(Collection<String> list){
		return toString(list, " ");
	}
	/**
	 * 将数组转换成字符串
	 * @author eko.zhan at 2017年12月21日 下午1:18:51
	 * @param list
	 * @param split
	 * @return
	 */
	public static String toString(Collection<String> list, String split){
		StringBuffer sb = new StringBuffer();
		for (String s : list){
			sb.append(s + split);
		}
		return sb.toString();
	}
}
