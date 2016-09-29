package action;

import entity.TeamMemberAwardInfo_AwardLevel;
import entity.TeamMemberAwardInfo_ContestLevel;
import util.Main;

import java.sql.Date;

/**
 * Created by QAQ on 2016/6/30 0030.
 */
public class TeamAward  extends BaseAction{
    int id;
    String awardTime_d;
    String username1;
    String username2;
    String username3;
    String name1;
    String name2;
    String name3;
    int contestLevel;
    int awardLevel;
    String text;


    public String addOrEdit(){
        if(!Main.loginUserPermission().getTeamMemberAdmin()) return "success";
        if(id==-1){
            //add
            Main.users.addTeamMemberAwardInfo(this);
        }else{
            //edit
            Main.users.updateTeamMemberAwardInfo(this);
        }
        return "success";
    }
    public String del(){
        if(!Main.loginUserPermission().getTeamMemberAdmin()) return "success";
        Main.users.delTeamMemberAwardInfo(id);
        return "success";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAwardTime_d() {

        return awardTime_d;
    }

    public void setAwardTime_d(String awardTime) {
        this.awardTime_d = awardTime;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
    }

    public String getUsername3() {
        return username3;
    }

    public void setUsername3(String username3) {
        this.username3 = username3;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public int getAwardLevel() {

        return awardLevel;
    }

    public void setAwardLevel(int awardLevel) {
        this.awardLevel = awardLevel;
    }

    public int getContestLevel() {

        return contestLevel;
    }

    public void setContestLevel(int contestLevel) {
        this.contestLevel = contestLevel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
