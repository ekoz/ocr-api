/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年2月2日 上午10:43:26
 * @version 1.0
 */
public class OcrApiUtils {
	
	public static final String UTF8 = "UTF-8";
	public static final String OS_LINUX = "LINUX";

	/**
	 * 判断当前操作系统是 linux or windows
	 * @author eko.zhan at 2017年12月19日 下午6:20:54
	 * @return
	 */
	public static boolean isLinux(){
		Properties prop = System.getProperties();
		String defaultOS = prop.getProperty("os.name").toUpperCase();
		if (defaultOS.indexOf(OS_LINUX) > -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * <a href="https://www.cnblogs.com/fclbky/p/6112180.html">用Runtime.getRuntime().exec()需要注意的地方</a>
	 * @author eko.zhan at Jan 4, 2018 5:41:50 PM
	 * @param input
	 */
	public static void loadStream(final InputStream input) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				Reader reader = new InputStreamReader(input);
				BufferedReader bf = new BufferedReader(reader);
				String line = null;
				try {
					while((line=bf.readLine())!=null) {
						
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
    }
}
