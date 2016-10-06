package action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import sun.net.www.http.HttpCaptureOutputStream;
import util.Tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015/11/21 0021.
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    protected  HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;
    protected PrintWriter out;
    private String prompt;
    protected BaseAction(){
        try {
            HttpServletResponse response = ServletActionContext.getResponse();
            if (response != null) response.setContentType("text/html;charset=utf-8");
        }catch (Exception ignored){ }
    }
    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        request=httpServletRequest;
        session = request.getSession();
//        if(isPost()){
            Tool.log("["+(isPost()?"post":"get")+"]"+request.getRequestURI()+(request.getQueryString()!=null?"?"+request.getQueryString():""));
//        }
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        response=httpServletResponse;
        try {
            out=response.getWriter();
        } catch (IOException e) {
            out=null;
        }
    }

    public boolean isGet() {
        return "GET".equals(this.request.getMethod());
    }

    public boolean isPost() {
        return "POST".equals(this.request.getMethod());
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
