package com.cqrd.five.Interceptor;

import com.cqrd.five.config.JwtProperties;
import com.cqrd.five.dto.UserInfo;
import com.cqrd.five.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shkstart
 * @create 2019-11-19 11:00
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class TestInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 目标方法执行之前执行
     *请求控制器之前执行
     * @param req
     * @param res
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        if ("OPTIONS".equals(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String token =  req.getHeader("token");
        System.out.println("拦截器里面的token："+token);
        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
            System.out.println("用户名"+userInfo.getUsername());
            if(userInfo.getUsername()==null){
                // res.setHeader("Errormsg","非法用户请重新登录");
                System.out.println("该请求已被拦截......");
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.setHeader("Errormsg","非法用户请重新登录");
            //res.setCharacterEncoding("utf-8");
            System.out.println("请求被拦截");
            return false;

        }
        return true;
    }
    /**
     * 跳转页面之前执行
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }
    /**
     * 跳转页面之后执行，释放资源等
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
