package Main.OJ;

import Main.status.Result;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该类已不用
 * OJ信息。包括表单信息等
 * Created by Administrator on 2015/5/21.
 */
public class OJ {
    private String name;
    private String URL;
    private String submitURL;
    private String problemURL;
    private String TitleSelect;
    private String statusURL;
        private String statusFormUser;
        private String statuSelect;
        private Map<String,Integer> statuMap;
    private String DesSelect;
    private String InputSelect;
    private String OutputSelect;
    private String SampleInputSelect;
    private String SampleOutputSelect;
    private Map<String,Result> resultMap;
    private String loginURL;
    public List<String> loginForm;
    public List<String> submitForm;
    public List<NameValuePair> defLoginForm;
    public List<NameValuePair> defSubmitForm;
    public Map<String,String>  languageMap;
    public OJ(String OJname){
        name=OJname;
        if(OJname.equals("BNUOJ")) this.setBNUOJ();
        else if(OJname.equals("HDU")) this.setHDU();
    }
    public String getSubmitURL(){
        return URL+submitURL;
    }
    public String getURL() { return URL; }
    public String getLoginURL() {
        return URL+loginURL;
    }
    public String getStatusURL() {
        return URL+statusURL;
    }
    public String getStatusURL(String user){
        return URL+statusURL+"?"+statusFormUser+"="+user;
    }
    public String getProblemURL(String pid){ return URL+problemURL+"?pid="+pid; }
    public int getElementOf(String s){
        return statuMap.get(s);
    }

    public String getStatuSelect() {
        return statuSelect;
    }
    public String getDesSelect() {
        return DesSelect;
    }
    public String getSampleInputSelect() {
        return SampleInputSelect;
    }

    public boolean isRES(String s){
        return resultMap.containsKey(s);
    }
    public Result getResultMap(String v){
        return resultMap.get(v);
    }
    public String getTitleSelect(){
        return TitleSelect;
    }
    public String getName(){return name;}
    public void setBNUOJ(){
        URL="http://www.bnuoj.com";
        submitURL="/v3/ajax/problem_submit.php";
        loginURL="/v3/ajax/login.php";
        problemURL="/v3/problem_show.php";
            DesSelect=".content-wrapper";
            InputSelect=".content-wrapper";
            OutputSelect=".content-wrapper";
            SampleInputSelect="pre";
            SampleOutputSelect="pre";
            TitleSelect="h2";

        statusURL="/contest/status.php";
        statusFormUser="showname";//用做筛选的表单项名称
        statuSelect="center table:nth-child(3) tr:nth-child(2)";//获取statu的选择器

        statuMap = new HashMap<String,Integer>();//statu结果对照
        statuMap.put("user",1);
        statuMap.put("rid",2);
        statuMap.put("pid",3);
        statuMap.put("res",4);
        statuMap.put("lan",5);
        statuMap.put("time",6);
        statuMap.put("memory",7);

        resultMap = new HashMap<String, Result>();//result结果对照
        resultMap.put("Accepted",Result.AC);
        resultMap.put("Wrong Answer",Result.WA);
        resultMap.put("Runtime Error",Result.RE);
        resultMap.put("Time Limit Exceed",Result.TLE);
        resultMap.put("Memory Limit Exceed",Result.MLE);
        resultMap.put("Output Limit Exceed",Result.OLE);
        resultMap.put("Presentation Error",Result.PE);
        resultMap.put("Compile Error",Result.CE);
        resultMap.put("Restricted Function",Result.DANGER);

        loginForm=new ArrayList<String>();//登录表单列表
        loginForm.add("username");
        loginForm.add("password");
        defLoginForm = new ArrayList<NameValuePair>();//登录的表单列表的默认项
        defLoginForm.add(new BasicNameValuePair("cksave","365"));//?????????cookie save?????????

        submitForm=new ArrayList<String>();//提交的表单
        submitForm.add("problem_id");
        submitForm.add("language");
        submitForm.add("source");
        defSubmitForm = new ArrayList<NameValuePair>();//提交的表单列表的默认项
        defSubmitForm.add(new BasicNameValuePair("isshare","1"));//?????????share code?????????
        defSubmitForm.add(new BasicNameValuePair("user_id","@username"));

        languageMap=new HashMap<String,String>();//语言列表
        languageMap.put("0","1");//C++
        languageMap.put("1","2");//C
        languageMap.put("2","3");//JAVA
    }
    public void setHDU(){
        URL="http://acm.hdu.edu.cn";
        submitURL="/submit.php?action=submit";
        loginURL="/userloginex.php?action=login";

        problemURL="/showproblem.php";
            DesSelect=".panel_content";
            InputSelect=".panel_content";
            OutputSelect=".panel_content";
            SampleInputSelect="pre";
            SampleOutputSelect="pre";
            TitleSelect="h1";

        statusURL="/status.php";
        statusFormUser="user";
        statuSelect="table:nth-child(2) tr:nth-child(4) table  tr:nth-child(3)";

        statuMap = new HashMap<String,Integer>();
        statuMap.put("user",9);
        statuMap.put("rid",1);
        statuMap.put("pid",4);
        statuMap.put("res",3);
        statuMap.put("lan",8);
        statuMap.put("time",5);
        statuMap.put("memory",6);

        resultMap = new HashMap<String, Result>();
        resultMap.put("Accepted",Result.AC);
        resultMap.put("Wrong Answer",Result.WA);
        resultMap.put("Runtime Error (ACCESS_VIOLATION)",Result.RE);
        resultMap.put("Runtime Error (ARRAY_BOUNDS_EXCEEDED)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_DENORMAL_OPERAND)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_DIVIDE_BY_ZERO)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_OVERFLOW)",Result.RE);
        resultMap.put("Runtime Error (FLOAT_UNDERFLOW)",Result.RE);
        resultMap.put("Runtime Error (INTEGER_DIVIDE_BY_ZERO)",Result.RE);
        resultMap.put("Runtime Error (INTEGER_OVERFLOW)",Result.RE);
        resultMap.put("Runtime Error (STACK_OVERFLOW)",Result.RE);
        resultMap.put("Time Limit Exceeded",Result.TLE);
        resultMap.put("Memory Limit Exceeded",Result.MLE);
        resultMap.put("Output Limit Exceeded",Result.OLE);
        resultMap.put("Presentation Error",Result.PE);
        resultMap.put("Compilation Error",Result.CE);
        resultMap.put("System Error",Result.DANGER);

        loginForm=new ArrayList<String>();
        loginForm.add("username");
        loginForm.add("userpass");
        submitForm=new ArrayList<String>();
        submitForm.add("problemid");
        submitForm.add("language");
        submitForm.add("usercode");

        languageMap=new HashMap<String,String>();
        languageMap.put("0", "0");
        languageMap.put("1", "1");
        languageMap.put("2", "5");
    }
}
