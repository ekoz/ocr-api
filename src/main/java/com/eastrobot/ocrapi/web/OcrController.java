/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.web;

import io.swagger.annotations.ApiOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.log.annotation.Interval;
import com.eastrobot.ocrapi.service.OcrService;

/**
 * 上传图片，输出图片中的文本
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月19日 下午2:33:05
 * @version 1.0
 */
@Controller
public class OcrController extends BaseController {

	private static final Log logger = LogFactory.getLog(OcrController.class);

	@Autowired
	private OcrService ocrService;
	
	/**
	 * 上传图片并识别图片中的文本
	 * @author eko.zhan at 2017年12月19日 下午2:51:11
	 * @param request
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@ApiOperation(value="传入视频或图片文件流，解析并返回内容", notes="返回json格式{\"summary\":\"You have to stop taking people at their wordl \",\"img\":\"QQ图片20140430203614.jpg\",\"isVideo\":false,\"text\":\"   别再信别人的了 !  You have to stop taking people at their wordl     \",\"keyword\":\"stop people You taking wordl \"}")
	@RequestMapping(value="/", method=RequestMethod.POST, produces="application/json;charset=utf-8")
	@ResponseBody
	@Interval
	public JSONObject parse(MultipartHttpServletRequest request) throws IOException, InterruptedException{
		MultipartFile file = request.getFile("file");
		String folder = ocrService.getFolderString();
		
		String inputPath = folder + "/" + file.getOriginalFilename();
		
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(inputPath));

		return ocrService.drive(inputPath);
	}
	
	@ApiOperation(value="根据指定的图片路径返回图片文件流")
	@RequestMapping(value="/ocr/loadImg", method=RequestMethod.GET)
	@ResponseBody
	public void loadImg(String path, HttpServletResponse response) throws IOException{
		String folder = ocrService.getFolderString();
		File file = new File(folder + "/" + path);
		FileInputStream inputStream = new FileInputStream(file);
		
        response.setContentType("image/" + path.substring(path.lastIndexOf(".")+1));
        ServletOutputStream out = response.getOutputStream();
        out.write(IOUtils.toByteArray(inputStream));
	}
	
	@ApiOperation(value="根据指定的视频路径返回视频文件流")
	@RequestMapping(value="/ocr/loadVideo", method=RequestMethod.GET)
	@ResponseBody
	public void loadVideo(String path, HttpServletResponse response) throws IOException{
		String folder = ocrService.getFolderString();
		File file = new File(folder + "/" + path);
		FileInputStream inputStream = new FileInputStream(file);
		
        response.setContentType("video/" + path.substring(path.lastIndexOf(".")+1));
        ServletOutputStream out = response.getOutputStream();
        out.write(IOUtils.toByteArray(inputStream));
	}
}
