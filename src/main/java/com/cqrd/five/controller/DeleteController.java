package com.cqrd.five.controller;

import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;
import com.cqrd.five.mapper.CrmMapper;
import com.cqrd.five.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class DeleteController {

    @Autowired
    private CrmMapper crmMapper;
    private Result result = new Result();
    @RequestMapping(value = "/deleteTest",method = RequestMethod.POST)
    private Result delete(HttpServletRequest request) {
        Map<String, String[]> key = request.getParameterMap();
        String[] usernames = key.get("deleteUserNameBox[]");
        int ulen = 0;
        for (String username:usernames) {
            ulen++;
        }
        Integer[] ids = crmMapper.deleteSelect(usernames);
        int ilen = 0;
        for (Integer id:ids) {
            ilen++;
        }
        if(ulen != ilen) {
            result.setMsg("不可删除管理员用户.");
        }
        System.out.println(ulen);
        System.out.println(ilen);
        System.out.println(result.getMsg());
        boolean b = crmMapper.delete(ids);
        if(!b){
            //抛出删除用户数据失败的异常
            throw new CustomException(ResultStatusEnum.Delete_User_failed);
        }
        result.setSuccess(b);
        return result;
    }

}
