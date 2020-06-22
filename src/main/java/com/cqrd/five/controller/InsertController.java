package com.cqrd.five.controller;

import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;
import com.cqrd.five.mapper.CrmMapper;
import com.cqrd.five.dto.Result;
import com.cqrd.five.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 添加用户数据
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class InsertController {

    @Autowired
    private CrmMapper crmMapper;
    private Result result = new Result();
    @RequestMapping(value = "/addTest",method = RequestMethod.POST)
    private Result insert(HttpServletRequest request) {
        String xzusername = request.getParameter("xzusername");
        String xztel = request.getParameter("xztel");
        String xzpw = request.getParameter("xzpw");
        Double xzgrade = Double.parseDouble(request.getParameter("xzgrade"));
        Double xzcredit = Double.parseDouble(request.getParameter("xzcredit"));
        User addUaer = new User();
        addUaer.setUserName(xzusername);
        addUaer.setPhone(xztel);
        addUaer.setGitAccount(xzpw);
        addUaer.setScore(xzgrade);
        addUaer.setCredit(xzcredit);
        boolean b = crmMapper.addupdate(addUaer);
        if(!b){
            //抛出添加用户数据失败异常
            throw new CustomException(ResultStatusEnum.Failed_To_Add_User);
        }
        result.setSuccess(b);
        return result;
    }

}
