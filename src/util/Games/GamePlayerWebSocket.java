package util.Games;

import WebSocket.MatchWebSocket;
import net.sf.json.JSONObject;
import org.apache.catalina.websocket.MessageInbound;
import util.Games.GoBang.GameGoBang;
import util.Tool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by QAQ on 2017/8/6.
 */
public class GamePlayerWebSocket extends MessageInbound implements IGamePlayer{

    private BlockingQueue<Integer> catch_queue = new LinkedBlockingQueue<>();
    private boolean is_Error = false;
    private GameGoBang game;
    private long timeOut = 30000;
    private String user;

    public GamePlayerWebSocket(String user){
        this.user = user;
    }

    @Override
    public void setTimeOut(long millisecond) {
        timeOut = millisecond;
    }

    public int getInt() throws GameReadException{
        if(is_Error) throw new GameReadException();
        try {
            return catch_queue.poll(timeOut, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException | InterruptedException | NullPointerException e) {
            is_Error = true;
//            try {
//                getWsOutbound().writeTextMessage(CharBuffer.wrap("{\"type\":\"end\"}".toCharArray()));
//            } catch (IOException ignored) {
//
//            }
            throw new GameReadException();
        }
    }
    public void putInt(int a) throws GameReadException{
        try {
            JSONObject step = new JSONObject();
            step.put("type","int");
            step.put("value",a);
            getWsOutbound().writeTextMessage(CharBuffer.wrap(step.toString()));
        }catch (IOException e){
            throw new GameReadException();
        }
    }

    @Override
    public void gameEnd(int a) {
        try {
            JSONObject step = new JSONObject();
            step.put("type","result");
            step.put("value",a);
            getWsOutbound().writeTextMessage(CharBuffer.wrap(step.toString()));
        }catch (IOException ignored){

        }
    }

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public String getAuthor() {
        return user;
    }

    @Override
    protected void onBinaryMessage(ByteBuffer byteBuffer) throws IOException {

    }

    @Override
    protected void onTextMessage(CharBuffer charBuffer) throws IOException {
        Tool.log(" recv:"+charBuffer);
        JSONObject jo = JSONObject.fromObject(charBuffer.toString());
        switch (jo.getString("type")){
            case "start":
                Tool.log("game start");
                new Thread(game).start();
                break;
            case "int":
                Tool.log("game recv int=" + jo.getInt("value"));
                catch_queue.offer(jo.getInt("value"));
                break;
        }
        /*
        Tool.log("game recv: "+charBuffer);
        try {
            boolean res = catch_queue.offer(Integer.parseInt(charBuffer.toString()));
            if(res) is_Error = true;
        }catch (NumberFormatException ignored){
        }*/
    }

    @Override
    protected void onClose(int status) {
        is_Error = true;
        Tool.log("game close status="+status);
    }

    public void setGame(GameGoBang game) {
        this.game = game;
    }
}
