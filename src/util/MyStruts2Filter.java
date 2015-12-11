package util;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/11 0011.
 */

public class MyStruts2Filter extends StrutsPrepareAndExecuteFilter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        //不过滤的url
        String url = request.getRequestURI();
        String contextPath=request.getContextPath();
        HttpServletResponse response = (HttpServletResponse) res;
        //System.out.println(url);
        if (url.endsWith("controller.jsp")) {
            //System.out.println("使用自定义的过滤器");
            chain.doFilter(req, res);
        }else{
            //System.out.println("使用默认的过滤器");
            super.doFilter(req, res, chain);
        }
    }
}