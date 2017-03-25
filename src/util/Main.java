package util;

import WebSocket.MatchWebSocket;
import action.App.ActionAppUpdate;
import dao.*;
import dao.Mall.GoodsSQL;
import entity.*;
import entity.OJ.OTHOJ;
import servise.GvMain;
import util.CodeCompare.cplusplus.CPlusPlusCompare;
import util.GlobalVariables.GlobalVariables;
import util.HTML.problemHTML;
import action.addLocalProblem;
import action.addproblem1;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import util.SQL.SQLUpdateThread;

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
    public static AcbOrderSQL acbOrderSQL = new AcbOrderSQL();

    public static Map<Integer,Set<MatchWebSocket>> sockets=new HashMap<>();
    public static SQLUpdateThread sqlUpdateThread = new SQLUpdateThread();

    public static void Init(){
        config = (new Config()).readConfig(GV);
        try {
            Class.forName(config.sqlclass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        status.init();
        new Thread(sqlUpdateThread).start();
    }
    public static void readConfig(){
        GV = GlobalVariables.read();
        config.readConfig(GV);
    }
    public static String addProblem(addproblem1 action){
        Problem p = new Problem();
        p.setType(action.getOjid()==-1?0:1);
        p.setTitle(action.getTitle());
        p.setAuthor(action.getAuthor());
        p.setOjid(action.getOjid());
        p.setOjspid(action.getOjspid());
        p.setSpj(action.getIsSpj()!=null);
        p.setPid(action.getPid());

        if(action.getPid() == -1) {//new
            int newpid = problems.addProblem(-1, p);
            p.setPid(newpid);

        }else{//edit
            Main.problems.editProblem(action.getPid(),p);
        }

        problemHTML ph = Main.problems.getProblemHTML(p.getPid());
        if(action.getPid() == -1&&(action.getOjid() != -1  && action.getOjid()!=7)) return "success";
        if(ph == null){
            ph = new problemHTML();
        }
        try {
            ph.setInt64(p.getType() == 0 ? "%I64d" : Submitter.ojs[p.getOjid()].get64IO(p.getOjspid()));
        }catch (Exception ignore){ }

        ph.setMenoryLimit(action.getMemory()+"MB");
        ph.setTimeLimit(action.getTime()+"MS");
        if(p.getPid()!=-1) problems.delProblemDis(p.getPid());
        problems.saveProblemHTML(p.getPid(), ph);
        FILE.createDirectory(p.getPid());

        /*
        if(action.getOjid() == -1 || action.getOjid() == 7){//本地题或者judge_system的题
            if(action.getPid() == -1){
                Problem p;
                if(action.getOjid() == -1)  p = new Problem(action.getTitle());
                else{
                    p = new Problem(action.getOjid(), action.getOjspid(), action.getTitle(), action.getAuthor(),action.getIsSpj() != null);
                }
                p.Author=action.getAuthor();
                p.spj = action.isSpj();
                int newpid=problems.addProblem(-1,p);
                problemHTML ph=new problemHTML();
                if(action.getOjid() == -1 ){
                    ph.setInt64("%I64d");
                }else{
                    ph.setInt64("%lld");
                }
                ph.setTimeLimit(action.getTime() + "MS");
                ph.setMenoryLimit(action.getMemory() + "MB");
                problems.saveProblemHTML(newpid, ph);
                FILE.createDirectory(newpid);
                return "success";
            }
            else{
                int pid=action.getPid();
                Problem p = new Problem(action.getOjid(), action.getOjspid(), action.getTitle(), action.getAuthor(), action.getIsSpj() != null);
                if(action.getOjid() == -1) p.setType(0);
                else{
                    p.setType(1);
                }
                p.Title=action.getTitle();
                p.Author=action.getAuthor();
                p.spj = action.isSpj();
                Main.problems.editProblem(pid,p);
                problemHTML ph=Main.problems.getProblemHTML(pid);
                ph.setTimeLimit(action.getTime()+"MS");
                ph.setMenoryLimit(action.getMemory()+"MB");
                if(action.getOjid() == -1) ph.setInt64("%I64d");//本地题
                else if(action.getOjid() == 7) ph.setInt64("%lld");//judge_system的题
                problems.delProblemDis(pid);
                problems.saveProblemHTML(pid,ph);
                return "success";
            }
        }else {
            Problem p = new Problem(action.getOjid(), action.getOjspid(), action.getTitle(), action.getAuthor(), action.getIsSpj() != null);
            problems.addProblem(action.getPid(), p);

            problemHTML ph=Main.problems.getProblemHTML(action.getPid());
            if(ph!=null) {
                ph.setTimeLimit(action.getTime() + "MS");
                ph.setMenoryLimit(action.getMemory() + "MB");
                if (action.getOjid() == -1) ph.setInt64("%I64d");//本地题
                else if (action.getOjid() == 7) ph.setInt64("%lld");//judge_system的题
                problems.delProblemDis(action.getPid());
                problems.saveProblemHTML(action.getPid(), ph);
            }
        }*/
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

    public static String svnAddFileCommit(){
        /*
        * svn add . --no-ignore --force
        * svn commit -m
        * */
        Runtime rt = Runtime.getRuntime();
        File dir = new File(Main.config.localJudgeWorkPath+"\\data");
        try {
            Process pro = rt.exec(config.svnPath+" add . --no-ignore --force",new String[]{},dir);
            try {

                pro.waitFor();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pro = rt.exec(config.svnPath+" commit -m \"\"",new String[]{},dir);
            try {
                pro.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }
    public static String svnDelFileCommit(String fileName){
        /*
        * svn delete fileName
        * svn commit -m
        * */
        Runtime rt = Runtime.getRuntime();
        try {
            File dir = new File(Main.config.localJudgeWorkPath+"\\data");
            Process pro = rt.exec(config.svnPath+" rm "+fileName,new String[]{},dir);
            try {
                String errorInfo = "";
                long time = System.currentTimeMillis();
                pro.waitFor();

                BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                while (time + 10000L > System.currentTimeMillis() && (errorInfo = br.readLine()) != null) {
                    errorInfo = errorInfo + "\n";
                }
                Tool.debug(errorInfo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pro = rt.exec(config.svnPath+" commit -m \"\"",new String[]{},dir);
            try {
                pro.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            return Main.users.getUser((String)getSession().getAttribute("user"));
        }catch (Exception e){
            return null;
        }
    }
    public static boolean canViewCode(Status s,User user) {
        return user != null && (user.getUsername().equals(s.getUser()) || user.getPermission().getViewCode() || Main.users.haveViewCode(user.getUsername(), s.getPid()) );
    }
    public static boolean canDownloadData(User u,int pid){
        if(u == null) return false;
        if(u.getPermission().havePermissions(PermissionType.addProblem)) return true;
        if(u.getPermission().havePermissions(PermissionType.partAddProblem)){
            Problem p = Main.problems.getProblem(pid);
            if(p != null && p.getOwner().equals(u.getUsername())) return true;
        }
        return Main.users.haveDownloadData(u.getUsername(), pid);
    }
    public static boolean canUploadTestData(User u,int pid){
        if(u == null) return false;
        if(u.getPermission().havePermissions(PermissionType.addProblem)) return true;
        if(u.getPermission().havePermissions(PermissionType.partAddProblem)){
            Problem p = Main.problems.getProblem(pid);
            if(p != null && p.getOwner().equals(u.getUsername())) return true;
        }
        return false;
    }
    public static String getIP(){
        return getRequest().getRemoteAddr();
    }

    public static double codeCmp(String code1,String code2){
        CPlusPlusCompare cmp = new CPlusPlusCompare();
        return cmp.getSimilarity(code1,code2);
    }
}
