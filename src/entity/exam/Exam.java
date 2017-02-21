package entity.exam;

import entity.IBeanCanCatch;
import entity.IBeanResultSetCreate;
import entity.RegisterUser;
import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import servise.ExamMain;
import util.Main;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * 考试类
 * data:
 * {problems:[[pid,score],[pid,score]]}
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam implements IBeanCanCatch, IBeanResultSetCreate{
    List<Base_Exam_problem> problems = new ArrayList<>();
    List<RegisterUser> users = new ArrayList<>();

    private int id;
    private String name;
    private Timestamp begintime;
    private Timestamp endtime;
    private String info;
    private String createuser;//创建人。创建人和超级管理员可以修改其配置
    private Timestamp catch_time;

    public boolean isPending(){
        return begintime.after(Tool.now());
    }

    public boolean isRunning(){
        Timestamp now = Tool.now();
        return endtime.before(now)&&endtime.after(Tool.now());
    }

    public boolean isEnd(){
        return endtime.before(Tool.now());
    }

    public String getStatuHTML(){
        Timestamp now=new Timestamp(System.currentTimeMillis());
        if(begintime.after(now)){
            return "<b style='color:green'>未开始</b>";//PENDING
        }else if(endtime.before(now)){
            return "<b>已结束</b>";//END
        }else{
            return "<b style='color:red'>进行中</b>";//RUNNING
        }
    }

    public String toJSON(){
        JSONObject ret=new JSONObject();
        ret.put("id",id);
        ret.put("name",name);
        ret.put("begintime",begintime.getTime());
        ret.put("endtime",endtime.getTime());
        ret.put("now",Tool.now().getTime());
        ret.put("info",info);

        if(Main.loginUser().getUsername().equals(createuser)){
            ret.put("admin",true);
        }else{
            ret.put("admim",false);
        }
        JSONArray pros=new JSONArray();
        if(!isPending()){
            for(Base_Exam_problem pro:problems){
                pros.add(pro.toJSON());
            }
        }
        ret.put("problems",pros);
        return ret.toString();
    }

    @Override
    public Timestamp getExpired() {
        return catch_time;
    }

    @Override
    public void setExpired(Timestamp t) {
        catch_time = t;
    }
    @Override
    public void init(ResultSet rs) throws SQLException {
        id=rs.getInt("id");
        name=rs.getString("name");
        begintime=rs.getTimestamp("begintime");
        endtime=rs.getTimestamp("endtime");
        info=rs.getString("info");
        createuser=rs.getString("createuser");
        JSONObject jo = JSONObject.fromObject(rs.getString("data"));
        JSONArray ja_problems=jo.getJSONArray("problems");
        for(int i=0;i<ja_problems.size();i++){
            JSONArray every_problem=ja_problems.getJSONArray(i);
            Base_Exam_problem problem = ExamMain.getProblem(every_problem.getInt(0));
            if(problem!=null) {
                problem.setScore(every_problem.getInt(1));
                problems.add(problem);
            }
        }
    }

    public int canin(User user){
        if(user==null) return 0;
        if(user.getUsername().equals(createuser)) return 2;
        for(RegisterUser ru:users){
            if(ru.getUsername().equals(user.getUsername())){
                if(ru.getStatu()==RegisterUser.STATUS_ACCEPTED||ru.getStatu()==RegisterUser.STATUS_APPENDED)
                    return 1;
                if(ru.getStatu()==RegisterUser.STATUS_ADMIN){
                    return 2;
                }
            }
        }
        return 0;
    }

    public String getData(){
        JSONObject jo =new JSONObject();
        JSONArray ja_problems=new JSONArray();
        for (Base_Exam_problem problem : problems) {
            JSONArray ja_everyProblem = new JSONArray();
            ja_everyProblem.add(problem.getId());
            ja_everyProblem.add(problem.getScore());
            ja_problems.add(ja_everyProblem);
        }
        jo.put("problems",ja_problems);
        return jo.toString();
    }

    public List<Base_Exam_problem> getProblems() {
        return problems;
    }

    public List<RegisterUser> getUsers() {
        return users;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Timestamp getBegintime() {
        return begintime;
    }

    public Timestamp getEndtime() {
        return endtime;
    }

    public String getInfo() {
        return info;
    }

    public String getCreateuser() {
        return createuser;
    }

    public Timestamp getCatch_time() {
        return catch_time;
    }
}
