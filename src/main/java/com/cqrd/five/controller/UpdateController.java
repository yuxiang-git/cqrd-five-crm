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

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class UpdateController {

    @Autowired
    private CrmMapper crmMapper;
    private Result result = new Result();

    @RequestMapping(value = "/updateTest",method = RequestMethod.POST)
    private Result update(HttpServletRequest request) {
        String upusername = request.getParameter("upusername");
        String uptel = request.getParameter("uptel");
        String upgitlab = request.getParameter("upgitlab");
        Double upgrade = Double.parseDouble(request.getParameter("upgrade"));
        Double upcredit = Double.parseDouble(request.getParameter("upcredit"));
        User addUaer = new User();
        addUaer.setUserName(upusername);
        addUaer.setPhone(uptel);
        addUaer.setGitAccount(upgitlab);
        addUaer.setScore(upgrade);
        addUaer.setCredit(upcredit);
        boolean b = crmMapper.addupdate(addUaer);
        if(!b){
            //抛出修改用户数据失败的异常
            throw new CustomException(ResultStatusEnum.Failed_To_Modify_User);
        }
        result.setSuccess(b);
        return result;
    }

}
