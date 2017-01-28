package entity;

import entity.Enmu.UserStarType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by QAQ on 2017/1/25.
 */
public class UserStarSet implements IBeanResultSetCreate{
    private Map<Integer,UserStar> problemStar;
    private Map<Integer,UserStar> statusStar;

    public UserStarSet(){
        problemStar = new HashMap<>();
        statusStar = new HashMap<>();
    }

    @Override
    public Object init(ResultSet rs) throws SQLException {
        do{
            UserStar us = new UserStar();
            us.init(rs);
            if(us.getType() == UserStarType.PROBLEM){
                problemStar.put(us.getStart_id(),us);
            }else if(us.getType() == UserStarType.STATUS){
                problemStar.put(us.getStart_id(),us);
            }
        }while(rs.next());
        return this;
    }

    public UserStar isProblemStar(int pid){
        return problemStar.get(pid);
    }

    public UserStar isStatusStar(int rid){
        return statusStar.get(rid);
    }

    public void setProblemStar(String username,int pid,String text){
        UserStar userStar = new UserStar();
        userStar.setText(text);
        userStar.setType(UserStarType.PROBLEM);
        userStar.setStart_id(pid);
        userStar.setUsername(username);
        problemStar.put(pid,userStar);
    }

    public void setStatusStar(String username,int rid,String text){
        UserStar userStar = new UserStar();
        userStar.setText(text);
        userStar.setType(UserStarType.STATUS);
        userStar.setStart_id(rid);
        userStar.setUsername(username);
        statusStar.put(rid,userStar);
    }
}
