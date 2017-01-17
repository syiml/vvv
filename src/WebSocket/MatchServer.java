package WebSocket;

import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.commons.lang.NullArgumentException;
import util.Main;
import util.Tool;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class MatchServer extends WebSocketServlet {
    /**
     * 给cid的连接用户发送消息
     * @param cid 比赛id
     * @param text 发送文本
     */
    private static void sendMessage(int cid,String text){
        Tool.debug("~发送消息：cid="+cid+"&text="+text);
        try{
            Set<MatchWebSocket> s=Main.sockets.get(cid);
            Tool.debug("hehe:"+cid+" size:"+s.size());
            for(MatchWebSocket mw:s){
                Tool.debug("hehe:"+mw.username);
                mw.send(text);
            }
        }catch(NullPointerException ignored){}
    }

    /**
     * 获取在线用户列表
     * @param cid 比赛id
     * @return 在线用户列表
     */
    private static Set<String> getOnlineUser(int cid){
        Set<String> ret=new TreeSet<String>();
        Set<MatchWebSocket> s=Main.sockets.get(cid);
        if(s!=null) {
            for (MessageWebSocket mw : s) {
                ret.add(mw.username);
            }
        }
        return ret;
    }

    /**
     * 给指定用户发送在线列表
     * @param cid cid
     * @param mws 用户socket
     */
    public static void sendOnlineUser(int cid,MatchWebSocket mws){
        JSONObject jo=new JSONObject();
        jo.put("type","OnlineUser");
        JSONArray ja=new JSONArray();
        Set<String> set=getOnlineUser(cid);
        for(String s:set){
            ja.add(s);
        }
        jo.put("data",ja);
        mws.send(jo.toString());
    }

    /**
     * 给指定用户发送注册信息
     * @param cid cid
     * @param mws 用户socket
     */
    public static void sendRegisterUserInfo(int cid,MatchWebSocket mws){
        List<User> list=Main.users.getRegisterUsers(cid);
        JSONObject jo=new JSONObject();
        jo.put("type","RegisterUserInfo");
        JSONObject ja=new JSONObject();
        for (User aList : list) {
            JSONObject user = new JSONObject();
            User u = aList;
            user.put("nick", u.getNick());
            user.put("name", u.getName());
            user.put("gender", u.getGender());
            user.put("school", u.getSchool());
            user.put("faculty", u.getFaculty());
            user.put("major", u.getMajor());
            user.put("cla", u.getCla());
            user.put("no", u.getNo());
            ja.put(u.getUsername(), user);
        }
        jo.put("data",ja);
        mws.send(jo.toString());
    }

    /**
     * 发送提交信息
     * @param cid 比赛id
     * @param rid runid
     * @param username 提交用户
     * @param result 结果 1 ac 2 wa -1 nores
     * @param time 提交时间
     */
    public static void sendStatus(int cid,int rid,int pid,String username,int result,Long time){
        JSONObject jo=new JSONObject();
        jo.put("type","status");
        jo.put("rid",rid);
        jo.put("pid",pid);
        jo.put("username",username);
        jo.put("result",result);
        jo.put("time",time);
        sendMessage(cid,jo.toString());
    }

    /**
     * 发送聊天消息
     * @param cid 比赛id
     * @param user 说话人
     * @param text 内容
     */
    public static void sendChat(int cid,String user,String text){
        JSONObject jo=new JSONObject();
        jo.put("type","chat");
        jo.put("user",user);
        jo.put("text",text);
        sendMessage(cid,jo.toString());
    }

    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest request) {
        User u=Main.users.getUser((String)request.getSession().getAttribute("user"));
        int cid=Integer.parseInt(request.getParameter("cid"));
        if(u!=null){
            Tool.log(u.getUsername()+"观战连接"+" cid="+request.getParameter("cid"));
            MatchWebSocket aSocket=new MatchWebSocket(u.getUsername(),cid,this);
            put(cid,aSocket);
            return aSocket;
        }else{
            Tool.log("游客连接");
            MessageWebSocket mw=new MatchWebSocket(null,-1,this);
            return mw;
        }
    }

    /**
     * 放入一个socket
     * @param cid 比赛id
     * @param aSocket socket
     */
    private void put(int cid,MatchWebSocket aSocket){
        if(!Main.sockets.containsKey(cid)){
            Main.sockets.put(cid,Collections.synchronizedSet(new HashSet<MatchWebSocket>()));
        }
        sendLogin(cid,aSocket.username);
        Main.sockets.get(cid).add(aSocket);
        //sendOnlineUser(cid,aSocket);
    }

    /**
     * 删除一个socket
     * @param cid 比赛id
     * @param mws socket
     */
    protected void close(int cid,MatchWebSocket mws){
        if(mws.username!=null&&Main.sockets.get(cid)!=null){
            Main.sockets.get(cid).remove(mws);
            sendLogout(cid,mws.username);
            if(Main.sockets.get(cid).size()==0){
                Main.sockets.remove(cid);
            }
        }
    }

    /**
     * 给用户发送上线通知 {"type":"login","user":username}
     * @param cid 比赛id
     * @param user 上线用户的用户名
     */
    private void sendLogin(int cid,String user){
        sendMessage(cid,"{\"type\":\"login\",\"user\":\""+user+"\"}");
    }

    /**
     * 给用户发送下线通知 {"type":"logout","user":username}
     * @param cid 比赛id
     * @param user 下线用户用户名
     */
    private void sendLogout(int cid,String user){
        sendMessage(cid,"{\"type\":\"logout\",\"user\":\""+user+"\"}");
    }
}
