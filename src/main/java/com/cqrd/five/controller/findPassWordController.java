package com.cqrd.five.controller;


import com.cqrd.five.dto.Result;
import com.cqrd.five.ex.CustomException;
import com.cqrd.five.ex.ResultStatusEnum;
import com.cqrd.five.service.UserService;

import com.cqrd.five.util.JedisPoolUtils;
import com.cqrd.five.util.PhoneCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class findPassWordController {
    private final static Logger logger = LoggerFactory.getLogger(findPassWordController.class);
    @Autowired
    private UserService userService;
    //发送验证码到邮箱
    @RequestMapping(value = "/sendCodeToEmail",method = RequestMethod.POST)
    @ResponseBody
    private Result sendCodeToEmail(HttpServletRequest request) throws MessagingException {
        String username = request.getParameter("userName");
        String email = request.getParameter("email");
        Result result = userService.sendCode(username, email);
        return result;
    }
    //验证验证码并修改密码
    @RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
    @ResponseBody
    private Result resetPassword(HttpServletRequest request){
        String username = request.getParameter("userName");
        String emailcode = request.getParameter("emailCode");
        String newpassword = request.getParameter("newPassWord");
        Result result = userService.resetPassword(username, emailcode, newpassword);
        return result;
    }
    //生成验证码
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            PhoneCode randomValidateCode = new PhoneCode();
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法
        } catch (Exception e) {
            logger.error("获取验证码失败>>>>", e);
            throw new CustomException(ResultStatusEnum.Failed_to_get_verification_code);
        }
    }

    /**
     * 校验验证码
     */
    @PostMapping("/checkVerify")
    public Result checkVerify(HttpServletRequest request) {
        Result result = new Result();
        String inputStr = request.getParameter("ICode");
        try{
            //从客戶端获取随机数
            Jedis jedis = JedisPoolUtils.getJedis();
            String codelistJson = jedis.get("codelistJson");
            String random = codelistJson.substring(1, codelistJson.length()-1);
            String datelistJson = jedis.get("datelistJson");

            Date newdate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String stringnewdate = sdf.format(newdate);
            if(stringnewdate.compareTo(datelistJson) > 0){
                result.setMsg("验证码过期");
                return result;
            }

            if(inputStr == null){
                result.setMsg("验证码校验失败");
                return result;
            }
            if (random == null) {
                result.setMsg("验证码校验失败");
                return result;
            }
            if (random.equals(inputStr)) {
                result.setMsg("验证成功");
                return result;
            } else {
                result.setMsg("验证码校验失败");
                return result;
            }
        } catch (Exception e){
            logger.error("验证码系统错误，请联系管理员", e);
            result.setMsg("验证码系统错误，请联系管理员");
            return result;
        }


    }

}

