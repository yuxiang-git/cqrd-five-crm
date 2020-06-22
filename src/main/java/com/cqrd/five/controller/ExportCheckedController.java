/*
package com.cqrd.five.controller;

import com.cqrd.five.dto.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class ExportCheckedController {

    private Result result = new Result();

    @RequestMapping(value = "/exportcheckedTest.html",method = RequestMethod.POST)
    public Result exportchecked(HttpServletRequest request) throws IOException {
        Map<String, String[]> key = request.getParameterMap();
        String[] usernames = key.get("deleteUserNameBox[]");
        for (String username:usernames) {
            System.out.print(username+",");
            result.setDetail(username);
        }
        ExcelDownloadsController excelDownloadsController=new ExcelDownloadsController();
        result = excelDownloadsController.downloadAllClassmate1(usernames,request);
        return result;

    }

}
*/
