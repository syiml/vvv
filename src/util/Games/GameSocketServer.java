package util.Games;

import WebSocket.SocketServer;
import util.Games.GoBang.GameGoBang;
import util.Tool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by syimlzhu on 2017/1/14.
 */
public class GameSocketServer extends ServerSocket implements Runnable {
    private static GameSocketServer server = null;
    private GameSocketServer(int port) throws IOException {
        super(port);
    }
    public static void init(){
        try {
            server = new GameSocketServer(12306);
            Tool.debug("game server begin");
            new Thread(server).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            Socket s1,s2;
            try {
                s1=server.accept();
                Tool.debug("new conn!");
                s2=server.accept();
                Tool.debug("new conn!");
                Tool.debug("game begin!");
                new Thread(new GameGoBang(15,15,new GamePlayerSocket(s1),new GamePlayerSocket(s2))).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}