package com.cqrd.five.controller;

import com.cqrd.five.config.JwtProperties;
import com.cqrd.five.dto.UserInfo;
import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;
import com.cqrd.five.mapper.CrmMapper;
import com.cqrd.five.dto.Result;
import com.cqrd.five.dto.User;
import com.cqrd.five.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class ShowOrdiController {

    @Autowired
    private CrmMapper crmMapper;
    @Autowired
    private JwtProperties jwtProperties;
    private Result result = new Result();

    @RequestMapping(value = "/showOrdi",method = RequestMethod.POST)
    private Result showOrdiInfo(HttpServletRequest request) throws Exception {
        System.out.println("请求成功！展示数据。");
        String token = request.getHeader("token");
        System.out.println(token);
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        List<User> list = crmMapper.showOrdiInfo();
        if(list==null){
            //抛出显示用户数据失败的异常
            throw new CustomException(ResultStatusEnum.Show_Data_Failed);
        }
        result.setDetail(list);
        result.setMsg("请求成功。");
        result.setSuccess(true);
        result.setUserInfo(userInfo);
        return result;
    }

}
