package com.cqrd.five.config;

import com.cqrd.five.Interceptor.TestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 使用WebMvcConfigurer可以来扩展SpringMVC的功能
 *
 * 在spring5.0之前可以继承WebMvcConfigurerAdapter此适配器进行配置，但spring5.0以后此适配器就被废弃（已被标注为@Deprecated），
 * 目前有两种解决方案，一种是直接实现WebMvcConfigurer，另一种是直接继承WebMvcConfigurationSupport，官方推荐第一种方案。
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean //将自定义拦截器注册到spring bean中
    public  TestInterceptor TestInterceptor(){
        return new TestInterceptor();
    }
    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/**");
    }

    /**
     * 自定义拦截规则
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns - 用于添加拦截规则
        // excludePathPatterns - 用户排除拦截
        registry.addInterceptor(TestInterceptor())
                .addPathPatterns("/**")
               // .excludePathPatterns("/index.html", "/", "/login.html","/show.html");
                .excludePathPatterns("/login","/regist","/getVerify","/checkVerify",
                        "/UserExcelDownloads","/exportcheckedTest","/UserExcelDownloads1","/import",/*导入导出*/
                        /*"/deleteTest","/addTest","/selectTest","/selectOrdiTest","/updateTest",增删改查*/
                        "/sendCodeToEmail","/resetPassword",/*邮件改密码*/
                        "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                        "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg","/**/*.html","/redis"/*,"/show","/showOrdi"*/);

    }
}
