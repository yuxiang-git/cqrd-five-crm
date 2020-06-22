package com.cqrd.five.Filter;

import com.cqrd.five.config.JwtProperties;
import com.cqrd.five.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebFault;
import java.io.IOException;
import java.util.Collection;
@Component
public class XssFilter  implements Filter {
    public static final String RULE = "(.*)(<script>|alert|%)+(.*)";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        Collection<String[]> valueCollections = request.getParameterMap().values();
        for (String[] values : valueCollections) {
            for (String value : values) {
                if(value.matches(RULE)){
                    request.setAttribute("msg", "非法参数");
                    request.getRequestDispatcher("Administrator.html").forward(request, response);
                    System.out.println("非法参数");
                    return;
                }
            }
        }

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
