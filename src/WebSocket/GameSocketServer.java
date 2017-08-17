package WebSocket;

import dao.AiSQL;
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
    @Override
    protected StreamInbound createWebSocketInbound(String s, HttpServletRequest request) {
        Tool.log("new game connect!!");
        int game_id = Integer.parseInt(request.getParameter("id"));
        String ai_user = request.getParameter("user");
        Tool.log("new game connect!! id="+game_id+" user="+ai_user);
        StreamInbound ret = null;
        if(game_id == 0){
            //int black = Tool.randNum(0,1);
            int black = 2;
            GamePlayerWebSocket player = new GamePlayerWebSocket();
            String ai_code = AiSQL.getInstance().getByKeyFromSQL(1).getCode();//TODO: getAICodeByUser(ai_user,game_id);
            GameGoBangAIPlayer ai = new GameGoBangAIPlayer(ai_code,ai_user);
            ai.game_id = Tool.now().getTime()%1000+"";
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
