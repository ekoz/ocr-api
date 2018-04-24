/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.ocrapi.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.service.ResponseMessage;

import com.alibaba.fastjson.JSONObject;
import com.eastrobot.log.annotation.Interval;
import com.eastrobot.ocrapi.service.OcrService;

/**
 * 上传图片，输出图片中的文本
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月19日 下午2:33:05
 * @version 1.0
 */
@RestController
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
	@ApiOperation(value="传入视频或图片文件流，解析并返回内容", response=ResponseMessage.class)
	@ApiImplicitParams({
	        @ApiImplicitParam(name="file", value="待转换文件", dataType="__file", required=true, paramType="form")
	})
	@PostMapping(
			value = "/",
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
	@Interval
	public JSONObject parse(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException{
		String folder = ocrService.getFolderString();
		
		String inputPath = folder + "/" + file.getOriginalFilename();
		
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(inputPath));

		return ocrService.drive(inputPath);
	}
	
	@ApiOperation(value="根据指定的图片路径返回图片文件流")
	@GetMapping("/ocr/loadImg")
	public void loadImg(@RequestParam String path, HttpServletResponse response) throws IOException{
		String folder = ocrService.getFolderString();
		File file = new File(folder + "/" + path);
		FileInputStream inputStream = new FileInputStream(file);
		
        response.setContentType("image/" + path.substring(path.lastIndexOf(".")+1));
        ServletOutputStream out = response.getOutputStream();
        out.write(IOUtils.toByteArray(inputStream));
	}
	
	@ApiOperation(value="根据指定的视频路径返回视频文件流")
	@GetMapping("/ocr/loadVideo")
	public void loadVideo(@RequestParam String path, HttpServletResponse response) throws IOException{
		String folder = ocrService.getFolderString();
		File file = new File(folder + "/" + path);
		FileInputStream inputStream = new FileInputStream(file);
		
        response.setContentType("video/" + path.substring(path.lastIndexOf(".")+1));
        ServletOutputStream out = response.getOutputStream();
        out.write(IOUtils.toByteArray(inputStream));
	}
}
