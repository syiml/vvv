package WebSocket;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.catalina.websocket.WebSocketServlet;
import util.Tool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by Administrator on 2015/11/25 0025.
 */
public class MessageWebSocket extends MessageInbound {
    String username;
    int cid;
    WsOutbound outbound;
    MessageWebSocket(String username,int cid){
        super();
        this.username=username;
        this.cid=cid;
    }
    @Override
    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {
        Tool.log(username+"::"+byteBuffer);
    }

    @Override
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        Tool.log(username+":"+charBuffer);
    }
    @Override
    protected void onOpen(WsOutbound outbound) {
        //Tool.log(username+"->open");
        this.outbound=outbound;
    }
    @Override
    protected void onClose(int status) {
        Tool.log(username+"->close cid="+cid+"&username="+username);
        SocketServer.sockets.remove(this);
    }
    public void send(String text){
        try {
            getWsOutbound().writeTextMessage(CharBuffer.wrap(text));
            Tool.debug("给"+username+"发送消息【"+text+"】成功");
        } catch (IOException e) {
            Tool.debug("给"+username+"发送消息【"+text+"】失败");
        }
    }
    public void close(){
        try {
            outbound.close(0,null);
        } catch (IOException ignored) {}
    }
}
