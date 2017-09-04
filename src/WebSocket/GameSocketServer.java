package WebSocket;

import dao.AiSQL;
import entity.AiInfo;
import entity.User;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import util.Games.GamePlayerWebSocket;
import util.Games.GoBang.GameGoBang;
import util.Games.GoBang.GameGoBangAIPlayer;
import util.Games.IGamePlayer;
import util.Main;
import util.Tool;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by QAQ on 2017/8/6.
 */
public class GameSocketServer extends WebSocketServlet {
    private static int next_id = 0;
    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest request) {
        Tool.log("new game connect!!");
        int game_id = Integer.parseInt(request.getParameter("game_id"));
        int id = Integer.parseInt(request.getParameter("id"));
        Tool.log("new game connect!! game_id="+game_id+" id="+id);
        StreamInbound ret = null;
        if(game_id == 1){
            int black = Tool.randNum(1,2);
            //int black = 2;
            GamePlayerWebSocket player = new GamePlayerWebSocket((String)request.getSession().getAttribute("user"));
            String ai_code = null;
            AiInfo aiInfo = AiSQL.getInstance().getBeanByKey(id);
            if(aiInfo != null){
                ai_code = aiInfo.getCode();
            }else{
                return null;
            }
            String ai_user = aiInfo.getUsername();
            GameGoBangAIPlayer ai = new GameGoBangAIPlayer(id,ai_code,ai_user);
            ai.game_id = next_id + "";
            next_id = (next_id + 1)%1000;
            GameGoBang gameGoBang = null;
            if(black == 1) {
                gameGoBang = new GameGoBang(15, 15, player , ai);
            }else{
                gameGoBang = new GameGoBang(15, 15, ai , player);
            }
            ai.setGameGoBang(gameGoBang);
            player.setGame(gameGoBang);
            ret = player;
            //new Thread(gameGoBang).start();
        }
        Tool.log("new game connect!! ret="+ret);
        return ret;
    }
}
