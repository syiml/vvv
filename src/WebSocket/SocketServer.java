package WebSocket;

import entity.User;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.struts2.ServletActionContext;
import util.Main;
import util.Tool;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class SocketServer extends WebSocketServlet {
    static Set<MessageWebSocket> sockets=new HashSet<MessageWebSocket>();

    public static void sendMessage(int cid,String text){
        Tool.log("发送消息：cid="+cid+"&text="+text);
        for(MessageWebSocket mw:sockets){
            if(mw.cid==cid){
                mw.send(text);
            }
        }
    }

    public static Set<String> getOnlineUser(int cid){
        Set<String> ret=new TreeSet<String>();
        for(MessageWebSocket mw:sockets){
            if(mw.cid==cid){
                ret.add(mw.username);
            }
        }
        return ret;
    }

    @Override
    protected MessageWebSocket createWebSocketInbound(String s, HttpServletRequest request) {
        try{
            User u=Main.loginUser();
            if(u!=null){
                Tool.log(u.getUsername()+"连接"+" cid="+request.getParameter("cid"));
                MessageWebSocket aSocket=new MessageWebSocket(u.getUsername(),Integer.parseInt(request.getParameter("cid")));
                sockets.add(aSocket);
                return aSocket;
            }else{
                Tool.log("游客连接");
                MessageWebSocket mw=new MessageWebSocket(null,-1);
                sockets.add(mw);
                return mw;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
