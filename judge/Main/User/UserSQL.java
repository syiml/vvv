package Main.User;

import Main.Main;
import Main.User.Permission;
import Main.User.User;
import Main.contest.RegisterUser;
import Main.status.Result;
import Message.Message;
import Message.MessageSQL;
import Tool.HTML.HTML;
import Tool.SQL;
import action.edit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2015/6/3.
 */
public class UserSQL {
    /*
    * users(username,password,nick,gender,Email,motto,registertime,type,solved,submissions,Mark)
    * permission(id,name)
    * userper(username,perid)
    * */
    public int register(User u){
        PreparedStatement p= null;
        try {
            p = Main.conn.prepareStatement("select * from users where username=?");
            p.setString(1, u.getUsername());
            ResultSet s=p.executeQuery();
            s.next();
            if(s.isLast()) return -1;//已存在
            p = Main.conn.prepareStatement("insert into users values(?,md5(?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            p.setString(1, u.getUsername());
            p.setString(2, u.getPassword());
            p.setString(3, HTML.HTMLtoString(u.getNick()));
            p.setInt(4, u.getGender());
            p.setString(5, HTML.HTMLtoString(u.getSchool()));
            p.setString(6, HTML.HTMLtoString(u.getEmail()));
            p.setString(7, HTML.HTMLtoString(u.getMotto()));
            p.setTimestamp(8, u.getRegistertime());
            p.setInt(9, u.getType());
            p.setString(10, HTML.HTMLtoString(u.getMark()));
            p.setInt(11, -100000);
            p.setInt(12,0);
            p.setInt(13, 0);
            p.setString(14, "");
            p.setString(15,"");
            p.setString(16,"");
            p.setString(17,"");
            p.setString(18,"");
            p.setString(19,"");
            p.setInt(20,0);
            p.executeUpdate();
            MessageSQL.addMessageWelcome(u);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
    public int getRank(String user){
        ResultSet rs=SQL.query("select rank+1 from v_user where username=?", user);
        try {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public Permission getPermission(String username){
        if(username==null) return null;
        PreparedStatement p;
        try{
            p=Main.conn.prepareStatement("select perid from userper where username=?");
            p.setString(1, username);
            ResultSet rs=p.executeQuery();
            return new Permission(rs);
        }catch(SQLException e){
            return null;
        }
    }
    public List<List<String>> getPermissionTable(){
        //user,per,admin
        ResultSet rs=SQL.query("SELECT username,perid,(select name from permission where id=perid) as name FROM userper");
        List<List<String>> table=new ArrayList<List<String>>();
        try {
            while(rs.next()){
                List<String> row=new ArrayList<String>();
                row.add(rs.getString("username"));
                row.add(rs.getString("name"));
                row.add(HTML.a("delper.action?user="+rs.getString("username")+"&perid="+rs.getString("perid"),"删除"));
                table.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return table;
    }
    public void addPer(String user,int per){
        SQL.update("INSERT INTO userper values(?,?)",user,per);
    }
    public void delPer(String user,int per){
        SQL.update("delete from userper where username=? and perid=?",user,per);
    }

    public User getUser(String username){
        return getUser(username,false);
    }
    public User getUserHaveRank(String username){
        return getUser(username,true);
    }
    private User getUser(String username,boolean rank){
        PreparedStatement p=null;
        try {
            if(rank) p=Main.conn.prepareStatement("select username,nick,gender,school,Email,motto,registertime,type,Mark,rating,rank+1 as rank,ratingnum,acb,name,faculty,major,cla,no,phone from v_user where username=? ");
            else p=Main.conn.prepareStatement("SELECT username,nick,gender,school,Email,motto,registertime,type,Mark,rating,-1 as rank,ratingnum,acb,name,faculty,major,cla,no,phone from users where username=?");
            p.setString(1,username);
            ResultSet rs=p.executeQuery();
            if(rs.next())
                return new User(rs);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return null;
    }
    public List<User> getUsers(int from,int num,String serach,String order,boolean desc){
        if(order==null||order.equals("")){
            order="rank";
        }
        List<User> list=new ArrayList<User>();
        PreparedStatement p=null;
        try {
            if(serach==null||serach.equals("")){
                p=Main.conn.prepareStatement("select username,nick,gender,school,Email,motto,registertime,type,Mark,rating,rank+1 as rank,ratingnum,acb,acnum,name,faculty,major,cla,no,phone " +
                        " from v_user "+
                                (order==null||order.equals("")?"":" ORDER BY "+order+(desc?" desc ":" "))+
                        " LIMIT "+from+","+num);
            }else{
                p=Main.conn.prepareStatement("select username,nick,gender,school,Email,motto,registertime,type,Mark,rating,rank+1 as rank,ratingnum,acb,acnum,name,faculty,major,cla,no,phone  from v_user where  (username like ? or nick like ?)  " +
                        (order==null||order.equals("")?"":" ORDER BY "+order+(desc?" desc ":" "))+
                        " LIMIT "+from+","+num);
                p.setString(1,"%"+serach+"%");
                p.setString(2,"%"+serach+"%");
            }
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                list.add(new User(rs));
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }
    public List<User> getRichTop10(){
        List<User> list=new ArrayList<User>();
        PreparedStatement p=null;
        try {
            p=Main.conn.prepareStatement("select username,nick,gender,school,Email,motto,registertime,type,Mark,rating,rank+1 as rank,ratingnum,acb,name,faculty,major,cla,no,phone from v_user order by acb desc,rating desc " +
                    "LIMIT 0,10");
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                list.add(new User(rs));
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }
    public List<List<String>> getUsers(int cid,int from,int num,String serach,boolean is3){
        List<List<String>> list=new ArrayList<List<String>>();
        PreparedStatement p=null;
        try {
            p=Main.conn.prepareStatement("select * from v_contestuser where cid=? and (username like ? or nick like ?) ORDER BY time desc" +
                    " LIMIT "+from+","+num);
            p.setInt(1,cid);
            p.setString(2,"%"+serach+"%");
            p.setString(3,"%"+serach+"%");
            ResultSet rs=p.executeQuery();
            while(rs.next()){
                List<String> s=new ArrayList<String>();
                String nick=rs.getString("nick");
                if(nick==null){
                    s.add(rs.getString("username"));
                    if(is3){
                        s.add("");
                        s.add("");
                        s.add("");
                        s.add("");
                        s.add("");
                        s.add("");
                    }
                    s.add(HTML.textb("未注册","red"));
                    s.add("");
                }else{
                    //HTML.a("UserInfo.jsp?user=" + rs.getString("username"), rs.getString("username"));
                    s.add(rs.getString("username"));
                    if(is3){
                        s.add(rs.getString("name"));
                        s.add(rs.getInt("gender")+"");
                        s.add(rs.getString("faculty"));
                        s.add(rs.getString("major"));
                        s.add(rs.getString("cla"));
                        s.add(rs.getString("no"));
                    }
                    s.add(rs.getString("nick"));
                    s.add(User.ratingToHTML(User.getShowRating(rs.getInt("ratingnum"), rs.getInt("rating"))));
                }
                if(rs.getInt("statu")==3){
                    s.add(
                            HTML.a("javascript:retry_show('" + rs.getString("username") +
                                    "','" +
                                    rs.getString("info") +
                                    "','" +
                                    cid +
                                    "'," +
                                    (Main.loginUser()!=null && Main.loginUser().username.equals(rs.getString("username"))) +
                                    ")"
                                    , RegisterUser.statuToHTML(rs.getInt("statu"))));
                }
                else s.add(RegisterUser.statuToHTML(rs.getInt("statu")));
                Timestamp t=rs.getTimestamp("time");
                if(t==null){
                    s.add("");
                }else{
                    s.add(t.toString().substring(0, 19));
                }
                s.add(rs.getString("info"));
                list.add(s);
            }
            rs.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return list;
        }
    }

    public String login(String user,String pass){
        try {
            ResultSet r=SQL.query("select username,password from users where username=? and password=md5(?)", user, pass);
            if(r.next()){
                return "LoginSuccess";
            }else{
                ResultSet rs=SQL.query("select * from users where username=?", user);
                if(rs.next()){
                    return "WrongPassword";
                }else{
                    return "NoSuchUser";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SystemError";
    }
    public String update(String username, edit e){
        String sql="UPDATE users set ";
        if(e.getNewpass()!=null&&!e.getNewpass().equals("")) sql+="password = md5('"+e.getNewpass()+"'),";
        sql+=" nick = '"+HTML.HTMLtoString(e.getNick())+"'";
        sql+=",name = '"+HTML.HTMLtoString(e.getName())+"'";
        sql+=",gender = "+e.getGender();
        sql+=",school = '"+HTML.HTMLtoString(e.getSchool())+"'";
        sql+=",faculty = '"+HTML.HTMLtoString(e.getFaculty_text())+"'";
        sql+=",major = '"+HTML.HTMLtoString(e.getMajor_text())+"'";
        sql+=",cla = '"+HTML.HTMLtoString(e.getCla())+"'";
        sql+=",no = '"+HTML.HTMLtoString(e.getNo()) +"'";
        sql+=",phone = '"+ HTML.HTMLtoString(e.getPhone())+"'";
        sql+=",Email = '"+HTML.HTMLtoString(e.getEmail())+"'";
        sql+=",motto = '"+HTML.HTMLtoString(e.getMotto())+"'";
        sql+=" WHERE username=?";
        SQL.update(sql, username);
        Main.getSession().setAttribute("user",Main.users.getUser(username,false));
        return "success";
    }

    public boolean haveViewCode(String user,int pid){
        ResultSet rs=SQL.query("SELECT * FROM t_viewcode WHERE username=? AND pid=?", user, pid);
        try {
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public Set<Integer> canViewCode(String user){
        ResultSet rs=SQL.query("SELECT pid FROM t_viewcode WHERE username=?",user);
        Set<Integer> list=new HashSet<Integer>();
        try {
            while(rs.next()){
                list.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int addViewCode(String user,int pid){
        return SQL.update("INSERT INTO t_viewcode VALUES(?,?)",user,pid);
    }

    public int awardACB(String user,int num,String text){
        if(num<=0) return 0;
        addACB(user,num);
        int x=MessageSQL.addMessageAwardACB(user,num,text);
        Main.log("管理员给"+user+" "+num+"ACB"+" 备注："+text);
        return x;
    }
    public int addACB(String user,int num){
        return SQL.update("UPDATE users SET acb=acb+? WHERE username=?",num,user);
    }
    public int subACB(String user,int num){
        return SQL.update("UPDATE users SET acb=acb-? WHERE username=? AND acb>=?",num,user,num);
    }

}
