package util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class ReDispatcherFilter implements Filter {
    public void destroy() {
        // TODO Auto-generated method stub
    }
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException
    {

        HttpServletRequest request = (HttpServletRequest)req;
        String target = request.getRequestURI();
        target = target.lastIndexOf("?")>0
                ?target.substring(target.lastIndexOf("/")+1,target.lastIndexOf("?")-target.lastIndexOf("/"))
                :target.substring(target.lastIndexOf("/")+1);

//        System.out.println(target);
        if(this.includes.contains(target))
        {
            RequestDispatcher rdsp = request.getRequestDispatcher(target);

//            System.out.println("go..............."+rdsp);
            rdsp.forward(req, resp);
        }
        else
            chain.doFilter(req, resp);
    }
    private ArrayList<String> includes = new ArrayList<String>();

    public void init(FilterConfig config) throws ServletException {

        this.includes.addAll( Arrays.asList(config.getInitParameter("includeServlets").split(",")));
    }
}
