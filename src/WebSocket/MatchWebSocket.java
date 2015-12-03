package WebSocket;

import org.apache.catalina.websocket.WsOutbound;
import util.HTML.HTML;
import util.Main;
import util.Tool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * Created by Administrator on 2015/12/2 0002.
 */
public class MatchWebSocket extends MessageWebSocket {
    MatchServer matchServer;
    MatchWebSocket(String username, int cid,MatchServer matchServer) {
        super(username, cid);
        this.matchServer=matchServer;
    }
    @Override
    protected void onClose(int status) {
        Tool.log(username+"->close cid="+cid+"&username="+username);
        matchServer.close(cid,this);
    }
    @Override
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        Tool.log(username+":=:"+charBuffer);
        Main.matchServer.sendChat(cid,username, HTML.HTMLtoString(charBuffer.toString()));
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        this.outbound=outbound;
        Main.matchServer.sendOnlineUser(cid,this);
        Main.matchServer.sendRegisterUserInfo(cid,this);
    }
}
