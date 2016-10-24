package util;

import WebSocket.MatchWebSocket;
import action.App.ActionAppUpdate;
import dao.*;
import dao.Mall.GoodsSQL;
import entity.OJ.OTHOJ;
import entity.Status;
import servise.GvMain;
import util.CodeCompare.cplusplus.CPlusPlusCompare;
import util.GlobalVariables.GlobalVariables;
import entity.Permission;
import entity.User;
import entity.Problem;
import util.HTML.problemHTML;
import action.addLocalProblem;
import action.addproblem1;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/5/21.
 * 实现业务逻辑和一些简单的杂项功能
 */
public class Main {
    public static JSONObject GV = GlobalVariables.read();
    public static Config config;

    public static ProblemSQL problems = new ProblemSQL();
    public static statusSQL status = new statusSQL();
    public static UserSQL users = new UserSQL();
    public static LogDao logs = new LogDao();
    public static Submitter submitter=new SubmitterImp();

    public static Map<Integer,Set<MatchWebSocket>> sockets=new HashMap<>();

    public static void Init(){
        config = (new Config()).readConfig(GV);
        try {
            Class.forName(config.sqlclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        status.init();
    }
    public static void readConfig(){
        GV = GlobalVariables.read();
        config.readConfig(GV);
    }
    public static String addProblem(addproblem1 action){
        Problem p=new Problem(action.getOjid(),action.getOjspid(),action.getTitle(),action.getAuthor(),action.getIsSpj()!=null);
        problems.addProblem(action.getPid(), p);
        return "success";
    }
    public static String addLocalProblem(addLocalProblem action){
        Problem p=new Problem(action.title);
        p.Author=action.getAuthor();
        p.spj = action.isSpj;
        int newpid=problems.addProblem(-1,p);
        problemHTML ph=new problemHTML();
        ph.setInt64("%I64d");
        ph.setTimeLimit(action.getTime() + "MS");
        ph.setMenoryLimit(action.getMemory() + "MB");
        problems.saveProblemHTML(newpid, ph);
        FILE.createDirectory(newpid);
        return "success";
    }
    public static String editLocalProblem(addLocalProblem action){
        int pid=action.getPid();
        Problem p=Main.problems.getProblem(pid);
        p.Title=action.getTitle();
        p.Author=action.getAuthor();
        p.spj = action.isSpj;
        Main.problems.editProblem(pid,p);
        problemHTML ph=Main.problems.getProblemHTML(pid);
        ph.setTimeLimit(action.getTime()+"MS");
        ph.setMenoryLimit(action.getMemory()+"MB");
        problems.delProblemDis(pid);
        problems.saveProblemHTML(pid,ph);
        return "success";
    }
    public static String setProblemVisiable(int pid){
        return problems.setProblemVisiable(pid);
    }
    public static problemHTML getProblemHTML(int pid){
        Problem p = problems.getProblem(pid);
        if(p == null) return null;
        problemHTML ph=Main.problems.getProblemHTML(pid);
        if(ph==null){
            OTHOJ oj= Submitter.ojs[p.getOjid()];
            ph=oj.getProblemHTML(p.getOjspid());
            //System.out.println("save:pid="+tpid);
            Main.problems.saveProblemHTML(pid,ph);
        }
        return ph;
    }
    public static MainResult appUpdate(ActionAppUpdate action){
        if(!Main.loginUserPermission().getAppUpdate()) return MainResult.NO_PERMISSION;
        try {
            uploadFile(action.getApp(), Main.getRealPath("/")+Main.config.appPath);
        }catch (IOException e){
            return MainResult.FAIL;
        }
        GvMain.setAppVersionName(action.getVersionName());
        GvMain.setAppVersionCode(action.getVersionCode());
        GvMain.setAppUpdate(action.getUpdate());
        return MainResult.SUCCESS;
    }

    public static Permission getPermission(String user){
        return users.getPermission(user);
    }
    public static String getCode(int rid){
        return status.getStatu(rid).getCode();
    }

    public static String getRealPath(String s){
        return getSession().getServletContext().getRealPath(s);
    }
    public static String uploadFile(File upload,String path) throws IOException {
        InputStream is=new FileInputStream(upload);
        OutputStream os=new FileOutputStream(path);
        byte buffer[] = new byte[1024];
        int count=0;
        while((count=is.read(buffer))>0){
            os.write(buffer,0,count);
        }
        os.close();
        is.close();
        return "success";
    }
    public static HttpSession getSession(){
        return ServletActionContext.getRequest().getSession();
    }
    public static PrintWriter getOut(){
        try {
            return ServletActionContext.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void saveURL(){
        String url=getRequest().getRequestURI();
        if(getRequest().getQueryString()!=null){
            url+="?"+getRequest().getQueryString();
        }
        getSession().setAttribute("url",url);
    }
    public static void saveURL(String s){
        getSession().setAttribute("url",s);
    }
    public static HttpServletRequest getRequest(){
        return ServletActionContext.getRequest();
    }
    public static Permission loginUserPermission(){
        User u=loginUser();
        if(u==null) return new Permission();
        else return getPermission(u.getUsername());
    }
    public static User loginUser(){
        try{
            return (User)getSession().getAttribute("user");
        }catch (Exception e){
            return null;
        }
    }
    public static boolean canViewCode(Status s,User user) {
        return user != null && (user.getUsername().equals(s.getUser()) || user.getPermission().getViewCode() || Main.users.haveViewCode(user.getUsername(), s.getPid()) );
    }
    public static String getIP(){
        return getRequest().getRemoteAddr();
    }

    public static double codeCmp(String code1,String code2){
        CPlusPlusCompare cmp = new CPlusPlusCompare();
        return cmp.getSimilarity(code1,code2);
    }
}
