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
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class SelectController {

    @Autowired
    private CrmMapper crmMapper;
    private Result result = new Result();
    @RequestMapping(value = "/selectTest",method = RequestMethod.POST)
    private Result select(HttpServletRequest request) {
        String selectcondition = request.getParameter("selectcondition");
        String info = request.getParameter("info");
        List<User> list = null;


            switch (selectcondition) {
                case "全部":
                    list = crmMapper.selectUserInfoByAll(info);
                    break;
                case "姓名":
                    list = crmMapper.selectUserInfoByName(info);
                    break;
                case "电话":
                    list = crmMapper.selectUserInfoByPhone(info);
                    break;
                case "gitlab":
                    list = crmMapper.selectUserInfoByGitAccount(info);
                    break;
                case "邮箱":
                    list = crmMapper.selectUserInfoByEmail(info);
                    break;
                case "成绩":
                    Double dinfo1 = Double.parseDouble(info);
                    list = crmMapper.selectUserInfoByScore(dinfo1);
                    break;
                case "学分":
                    Double dinfo2 = Double.parseDouble(info);
                    list = crmMapper.selectUserInfoByCredit(dinfo2);
                    break;
            }
            ;
            if (list == null) {
                //抛出查询数据失败的异常
                throw new CustomException(ResultStatusEnum.Query_Data_Failed);
            }
            result.setDetail(list);
            //result.setMsg("查询成功。");
            return result;
        }






}
