/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

import com.eastrobot.log.annotation.Interval;
import com.eastrobot.ocrapi.util.ListUtils;
import com.eastrobot.ocrapi.util.OcrApiUtils;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2018年2月2日 上午11:11:14
 * @version 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {
	private static final Log logger = LogFactory.getLog(VideoServiceImpl.class);
	private final static ExecutorService ffmpegExecutor = Executors.newSingleThreadExecutor();
	
	@Value("${ocr.output}")
	private String OUTPUT_FOLDER;
	@Value("${ocr.ffmpeg}")
	private String FFMPEG_PATH;
	
	@Resource
	private ImageService imageService;
	
	@Override
	@Interval
	public String handle(String inputPath) {
		Set<String> lineSet = new TreeSet<String>();
		List<String> lineList = new ArrayList<String>();
		//视频文件
		try {
			Future<?> future = runFfmpegCmd(inputPath, null);
			future.get();
			String outputPath = getFolderString(inputPath);
			File dir = new File(outputPath);
			for (File imgFile : dir.listFiles()){
				if (imgFile.isFile()){
					imageService.handle(imgFile.getAbsolutePath());
				}
			}
			for (File imgFile : dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(".txt")) return true;
					return false;
				}
			})){
				//TODO 如何去除重复
				//DONE 用Set，而且需要保证顺序
				List<String> readLines = IOUtils.readLines(new FileInputStream(imgFile), OcrApiUtils.UTF8);
				if (lineSet.addAll(readLines)){
					lineList.addAll(readLines);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return ListUtils.toString(lineList);
	}

	public Future<?> runFfmpegCmd(final String videoPath, Double frameRate) {
		frameRate = frameRate==null?0.2:frameRate;
		final Double frameRateString = frameRate;
		
		ListenableFutureTask<String> task = new ListenableFutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				try {
					List<String> commandLines = new ArrayList<String>();
					Process process = null;
					String folder = getFolderString(videoPath);
					File file = new File(folder + "/");
					if (!file.exists()){
						file.mkdirs();
					}
					if (OcrApiUtils.isLinux()){
						commandLines.add("ffmpeg"); //字符串拼接存在路径空格的问题 @Also See ProcessBuilder
						
					}else{
						commandLines.add(FFMPEG_PATH + "/ffmpeg.exe"); //字符串拼接存在路径空格的问题 @Also See ProcessBuilder
					}
					commandLines.add("-i");
					commandLines.add(videoPath);
					commandLines.add("-r");
					commandLines.add(String.valueOf(frameRateString));
					commandLines.add(folder + "/%003d.png");
					process = Runtime.getRuntime().exec(commandLines.toArray(new String[commandLines.size()]));
					OcrApiUtils.loadStream(process.getInputStream());
					OcrApiUtils.loadStream(process.getErrorStream());
					process.waitFor();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "";
			}
		});
		
		task.addCallback(new ListenableFutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				logger.debug(result);
				logger.debug("执行成功");
			}
			@Override
			public void onFailure(Throwable ex) {
				logger.debug("执行失败，原因：" + ex.getMessage());
			}
		});
		
		return ffmpegExecutor.submit(task);
	}
	
	/**
	 * 获取视频切割的图片所在的目录
	 * @author eko.zhan at 2018年2月2日 上午11:18:19
	 * @param inputPath
	 * @return
	 */
	public synchronized String getFolderString(String inputPath) {
		inputPath = inputPath.replaceAll("\\\\", "/");
		String parentFolderName = inputPath.substring(0, inputPath.lastIndexOf("/"));
		String folderName = inputPath.substring(inputPath.lastIndexOf("/")+1, inputPath.lastIndexOf("."));
		String folder = parentFolderName + "/" + folderName;
		return folder;
	}
}
