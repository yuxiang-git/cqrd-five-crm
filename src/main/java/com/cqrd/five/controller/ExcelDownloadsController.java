package com.cqrd.five.controller;

import com.cqrd.five.dto.Result;
import com.cqrd.five.dto.User;
import com.cqrd.five.ex.CustomException;
import com.cqrd.five.service.UserService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author shkstart
 * @create 2019-11-15 9:13
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class ExcelDownloadsController {
    @Autowired
    private UserService userService;
    private List<User> classmateList1;

    /**
     * 导出数据为Excel
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/UserExcelDownloads", method = RequestMethod.GET)
    public Result downloadAllClassmate(HttpServletResponse response) throws IOException {
        Result result=new Result();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        List<User> classmateList = userService.deriveByAll();
        String fileName = "用户信息表"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "编号","用户名", "姓名","邮箱","电话","gitlab账号","成绩","学分"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
        //在表中存放查询到的数据放入对应的列
        for (User user : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            if(user.getUserId()==null){
                user.setUserId(0);
            }
            row1.createCell(0).setCellValue(user.getUserId());
            if(user.getUserName()==null){
                user.setUserName("");
            }
            row1.createCell(1).setCellValue(user.getUserName());
            if(user.getChineseName()==null){
                user.setChineseName("");
            }
            row1.createCell(2).setCellValue(user.getChineseName());
            if(user.getEmail()==null){
                user.setEmail("");
            }
            row1.createCell(3).setCellValue(user.getEmail());
            if(user.getPhone()==null){
                user.setPhone("");
            }
            row1.createCell(4).setCellValue(user.getPhone());
            if(user.getGitAccount()==null){
                user.setGitAccount("");
            }
            row1.createCell(5).setCellValue(user.getGitAccount());
            if(user.getScore()==null){
                user.setScore(0.0);
            }
            row1.createCell(6).setCellValue(user.getScore());
            if(user.getCredit()==null){
                user.setCredit(0.0);
            }
            row1.createCell(7).setCellValue(user.getCredit());
            rowNum++;
        }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            result.setSuccess(true);
            result.setMsg("导出成功");
        }catch (CustomException e){
            e.printStackTrace();
            result.setMsg("导出失败");
            result.setSuccess(false);
        }
        return result;
    }
    @RequestMapping(value = "/exportcheckedTest",method = RequestMethod.POST)
    @ResponseBody
    public void downloadAllClassmate1(@RequestBody List<String> deleteUserNameBox)  {
        System.out.println("导出请求");
        System.out.println("str:"+deleteUserNameBox);
        String[] username = deleteUserNameBox.toArray(new String[deleteUserNameBox.size()]);
        classmateList1 = userService.findUserByExportChecked(username);
        System.out.println(classmateList1);
        System.out.println("导出对象变量赋值完成.");
    }
    @RequestMapping(value = "/UserExcelDownloads1", method = RequestMethod.GET)
    public Result downloadAllClassmate2(HttpServletResponse response) throws IOException {
        Result result=new Result();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");
        System.out.println("正在导出数据:"+classmateList1);
        List<User> classmateList = classmateList1;
        System.out.println("username:"+classmateList);
        String fileName = "用户信息表"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "编号","用户名", "姓名","邮箱","电话","gitlab账号","成绩","学分"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
            //在表中存放查询到的数据放入对应的列
            for (User user : classmateList) {
                HSSFRow row1 = sheet.createRow(rowNum);
                if(user.getUserId()==null){
                    user.setUserId(0);
                }
                row1.createCell(0).setCellValue(user.getUserId());
                if(user.getUserName()==null){
                    user.setUserName("");
                }
                row1.createCell(1).setCellValue(user.getUserName());
                if(user.getChineseName()==null){
                    user.setChineseName("");
                }
                row1.createCell(2).setCellValue(user.getChineseName());
                if(user.getEmail()==null){
                    user.setEmail("");
                }
                row1.createCell(3).setCellValue(user.getEmail());
                if(user.getPhone()==null){
                    user.setPhone("");
                }
                row1.createCell(4).setCellValue(user.getPhone());
                if(user.getGitAccount()==null){
                    user.setGitAccount("");
                }
                row1.createCell(5).setCellValue(user.getGitAccount());
                if(user.getScore()==null){
                    user.setScore(0.0);
                }
                row1.createCell(6).setCellValue(user.getScore());
                if(user.getCredit()==null){
                    user.setCredit(0.0);
                }
                row1.createCell(7).setCellValue(user.getCredit());
                rowNum++;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
            result.setSuccess(true);
            result.setMsg("导出成功");
        }catch (CustomException e){
            e.printStackTrace();
            result.setMsg("导出失败");
            result.setSuccess(false);
        }
        System.out.println(result.isSuccess());
        return result;
    }
}
