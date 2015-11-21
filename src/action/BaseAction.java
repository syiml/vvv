package action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import sun.net.www.http.HttpCaptureOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    protected  HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;


    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        request=httpServletRequest;
        session = request.getSession();
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        response=httpServletResponse;
    }

    public boolean isGet() {
        return "GET".equals(this.request.getMethod());
    }

    public boolean isPost() {
        return "POST".equals(this.request.getMethod());
    }
}
