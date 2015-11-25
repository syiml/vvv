package action;

import WebSocket.SocketServer;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class WebSocket extends BaseAction{

    public String text;
    public int cid;
    public String sendToContest(){
        if(isGet()){
            try {
                if(text!=null){//处理中文乱码
                    byte source [] = new byte[0];
                    source = text.getBytes("iso8859-1");
                    text = new String (source,"UTF-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SocketServer.sendMessage(cid,text);
        return SUCCESS;
    }
    public String getOnlieUserList(){
        Set<String> users=SocketServer.getOnlineUser(cid);
        StringBuilder sb=new StringBuilder("[");
        boolean isFirst=true;
        for(String s:users){
            if(isFirst){
                sb.append("\"").append(s).append("\"");
                isFirst=false;
            }else{
                sb.append(",\"").append(s).append("\"");
            }
        }
        sb.append("]");
        out.print(sb.toString());
        return null;
    }

    public String getText() {
        return text;
    }

    public int getCid() {
        return cid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }
}
