/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.ocrapi.util.ChineseUtil;
import com.eastrobot.ocrapi.util.FileExtensionUtils;
import com.eastrobot.ocrapi.util.ListUtils;
import com.hankcs.hanlp.HanLP;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月21日 下午3:52:29
 * @version 1.0
 */
@Component
public class OcrServiceImpl implements OcrService {
	private static final Log logger = LogFactory.getLog(OcrService.class);
	
	@Value("${ocr.output}")
	private String OUTPUT_FOLDER;
	@Resource
	private ImageService imageService;
	@Resource
	private VideoService videoService;

	@Override
	public String getFolderString() {
		return OUTPUT_FOLDER + "/" + DateFormatUtils.format(new Date(), "yyyyMMdd");
	}
	
	@Override
	public JSONObject drive(String inputPath) {
		File inputFile = new File(inputPath);
		String filename = inputFile.getName();
		String text = new String();
		JSONObject json = new JSONObject();
		
		String content = "";
		if (FileExtensionUtils.isVideo(filename)){
			//视频文件
			text = videoService.handle(inputPath);
			content = text.toString().replaceAll("\\s{2,}", " ");
			json.put("isVideo", true);
			json.put("img", filename);
			
		}else if (FileExtensionUtils.isImage(filename)){
			//图片文件
			text = imageService.handle(inputPath);
			content = text.toString().replaceAll("\\s{2,}", " ");
			json.put("isVideo", false);
			json.put("img", filename);
		}else{
			text = "仅支持图片和视频文件";
		}
		content = content.trim();
		//TODO content 内容自动纠正，以及移除生僻字
		logger.debug(content);
//		content = content.replaceAll("[\\?!，。？！一′'\\\\_\\[\\]]", "");
		text = ChineseUtil.removeMessy(content);
		logger.debug(text);
		
		List<String> summaryList = HanLP.extractSummary(text, 3);
		List<String> keywordList = HanLP.extractKeyword(text, 5);
		json.put("summary", ListUtils.toString(summaryList));
		json.put("keyword", ListUtils.toString(keywordList));
		json.put("text", text);
		return json;
	}
}
