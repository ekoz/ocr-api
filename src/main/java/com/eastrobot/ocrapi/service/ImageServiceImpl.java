/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eastrobot.log.annotation.Interval;
import com.eastrobot.ocrapi.util.ListUtils;
import com.eastrobot.ocrapi.util.OcrApiUtils;
/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年2月2日 上午10:45:49
 * @version 1.0
 */
@Service
public class ImageServiceImpl implements ImageService {
	private static final Log logger = LogFactory.getLog(ImageServiceImpl.class);
	private final static ExecutorService tesseractExecutor = Executors.newSingleThreadExecutor();
	@Value("${ocr.tesseract}")
	private String TESSERACT_PATH;
	
	@Override
	@Interval
	public String handle(String inputPath) {
		try {
			Future<?> future = runTesseractCmd(inputPath);
			future.get();
			List<String> lineList = IOUtils.readLines(new FileInputStream(inputPath + ".txt"), OcrApiUtils.UTF8);
			return ListUtils.toString(lineList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return "";
	}

	private Future<?> runTesseractCmd(final String inputPath) {
		return tesseractExecutor.submit(new Runnable() {
			@Override
			public void run() {
				String outputPath = inputPath;
				try {
					List<String> commandLines = new ArrayList<String>();
					Process process = null;
					if (OcrApiUtils.isLinux()){
						commandLines.add("tesseract");
					}else{
						commandLines.add(TESSERACT_PATH + "/tesseract"); //字符串拼接存在路径空格的问题 @Also See ProcessBuilder
					}
					commandLines.add(inputPath);
					commandLines.add(outputPath);
					commandLines.add("-l");
					commandLines.add("chi_sim");
//					commandLines.add("--psm");
//					commandLines.add("3");
//					commandLines.add("--oem");
//					commandLines.add("2");
					process = Runtime.getRuntime().exec(commandLines.toArray(new String[commandLines.size()]));
					
					OcrApiUtils.loadStream(process.getInputStream());
					OcrApiUtils.loadStream(process.getErrorStream());
					process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
