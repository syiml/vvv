package action;

import Challenge.ChallengeJSON;
import Challenge.ChallengeMain;
import Challenge.ChallengeSQL;
import Main.Main;
import org.apache.struts2.ServletActionContext;

/**
 * Created by Syiml on 2015/10/10 0010.
 */
public class ChallengeAction {
    public String getUser() {
        return user;
    }
    public String getId() {
        return id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public String getPar() {
        return par;
    }
    public String getNum() {
        return num;
    }
    public String getBlock() {
        return block;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setPar(String par) {
        this.par = par;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public void setBlock(String block) {
        this.block = block;
    }
    public String getPos() {
        return pos;
    }
    public String getScore() {
        return score;
    }
    public String getPid() {
        return pid;
    }
    public void setPos(String pos) {
        this.pos = pos;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }

    public String user;
    public String id;
    public String text;

    public String type;
    public String par;
    public String num;
    public String block;

    public String pos;
    public String score;
    public String pid;

    public String init(){
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        Main.getOut().println(ChallengeJSON.getBlockList(user));
        return "none";
    }
    public String block(){
        if(user==null||user.equals("")) user=Main.loginUser().getUsername();
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        Main.getOut().println(ChallengeJSON.getBlock(Integer.parseInt(id), user));
        return "none";
    }
    public String editBlockText(){
        if(Main.loginUserPermission().getChallengeAdmin()){
            return ChallengeSQL.editBlockText(Integer.parseInt(id), text);
        }
        return "error";
    }
    public String addCondition(){
        if(Main.loginUserPermission().getChallengeAdmin()) {
            return ChallengeSQL.addCondition(Integer.parseInt(id), Integer.parseInt(type), Integer.parseInt(block), Integer.parseInt(num));
        }
        return "error";
    }
    public String delCondition(){
        if(Main.loginUserPermission().getChallengeAdmin()) {
            return ChallengeSQL.delCondition(Integer.parseInt(id));
        }
        return "error";
    }
    public String addProblem(){
        if(Main.loginUserPermission().getChallengeAdmin()) {
            return ChallengeSQL.addProblem(Integer.parseInt(pos),Integer.parseInt(block), Integer.parseInt(pid),Integer.parseInt(score));
        }
        return "error";
    }
    public String delProblem(){
        if(Main.loginUserPermission().getChallengeAdmin()) {
            return ChallengeSQL.delProblem(Integer.parseInt(pos), Integer.parseInt(block));
        }
        return "error";
    }
}
