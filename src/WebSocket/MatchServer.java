package WebSocket;

import entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import util.Main;
import util.Tool;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class MatchServer extends WebSocketServlet {
    Map<Integer,Set<MatchWebSocket>> sockets=new HashMap<Integer, Set<MatchWebSocket>>();
    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest request) {
        if(Main.matchServer==null) Main.matchServer=this;
        User u=(User)request.getSession().getAttribute("user");
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
        if(!sockets.containsKey(cid)){
            sockets.put(cid,new HashSet<MatchWebSocket>());
        }
        sendLogin(cid,aSocket.username);
        sockets.get(cid).add(aSocket);
        //sendOnlineUser(cid,aSocket);
    }

    /**
     * 删除一个socket
     * @param cid 比赛id
     * @param mws socket
     */
    protected void close(int cid,MatchWebSocket mws){
        if(mws.username!=null&&sockets.get(cid)!=null){
            sockets.get(cid).remove(mws);
            sendLogout(cid,mws.username);
            if(sockets.get(cid).size()==0){
                sockets.remove(cid);
            }
        }
    }

    /**
     * 给cid的连接用户发送消息
     * @param cid 比赛id
     * @param text 发送文本
     */
    private void sendMessage(int cid,String text){
        Tool.log("~发送消息：cid="+cid+"&text="+text);
        Set<MatchWebSocket> s=sockets.get(cid);
        Tool.log("hehe:"+cid+" size:"+s.size());
        for(MatchWebSocket mw:s){
            Tool.log("hehe:"+mw.username);
            mw.send(text);
        }
    }

    /**
     * 获取在线用户列表
     * @param cid 比赛id
     * @return 在线用户列表
     */
    private Set<String> getOnlineUser(int cid){
        Set<String> ret=new TreeSet<String>();
        Set<MatchWebSocket> s=sockets.get(cid);
        for(MessageWebSocket mw:s){
            ret.add(mw.username);
        }
        return ret;
    }

    /**
     * 给指定用户发送在线列表
     * @param cid cid
     * @param mws 用户socket
     */
    private void sendOnlineUser(int cid,MatchWebSocket mws){
        JSONObject jo=new JSONObject();
        jo.put("type","OnleUser");
        JSONArray ja=new JSONArray();
        Set<String> set=getOnlineUser(cid);
        for(String s:set){
            ja.add(s);
        }
        jo.put("data",ja);
        mws.send(ja.toString());
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

    /**
     * 发送提交信息
     * @param cid 比赛id
     * @param rid runid
     * @param username 提交用户
     * @param result 结果 1 ac 2 wa -1 nores
     * @param time 提交时间
     */
    public void sendStatus(int cid,int rid,int pid,String username,int result,Long time){
        JSONObject jo=new JSONObject();
        jo.put("type","status");
        jo.put("rid",rid);
        jo.put("pid",pid);
        jo.put("username",username);
        jo.put("result",result);
        jo.put("time",time);
        sendMessage(cid,jo.toString());
    }
}
