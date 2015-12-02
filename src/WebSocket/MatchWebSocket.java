package WebSocket;

import util.Tool;

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
}
