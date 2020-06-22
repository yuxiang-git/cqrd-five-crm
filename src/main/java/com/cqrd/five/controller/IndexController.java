package com.cqrd.five.controller;

import com.cqrd.five.dto.Result;
import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;
import com.cqrd.five.service.UserService;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class IndexController {

	@Autowired
	private UserService userService;


	/**
	 * 导入
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public Result upload(@RequestParam("file") MultipartFile file) {
		Result result=new Result();
		String fileName = file.getOriginalFilename();
		boolean a = false;
		try {
			a = userService.batchImport(fileName, file);
			if (a==false){
				throw new CustomException(ResultStatusEnum.Import_failed);
			}
			result.setSuccess(true);
			result.setMsg("导入成功");
		} catch (CustomException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("导入失败");
		}
		return result;
	}




	/**
	 * 获取样式
	 *
	 * @param hssfWorkbook
	 * @param styleNum
	 * @return
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook hssfWorkbook, Integer styleNum) {
		HSSFCellStyle style = hssfWorkbook.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);//右边框
		style.setBorderBottom(BorderStyle.THIN);//下边框

		HSSFFont font = hssfWorkbook.createFont();
		font.setFontName("微软雅黑");//设置字体为微软雅黑

		HSSFPalette palette = hssfWorkbook.getCustomPalette();//拿到palette颜色板,可以根据需要设置颜色
		switch (styleNum) {
			case (0): {//HorizontalAlignment
				style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//跨列居中
				font.setBold(true);//粗体
				font.setFontHeightInPoints((short) 14);//字体大小
				style.setFont(font);
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			break;
			case (1): {
				font.setBold(true);//粗体
				font.setFontHeightInPoints((short) 11);//字体大小
				style.setFont(font);
			}
			break;
			case (2): {
				font.setFontHeightInPoints((short) 10);
				style.setFont(font);
			}
			break;
			case (3): {
				style.setFont(font);
				style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			}
			break;
		}

		return style;
	}



}
