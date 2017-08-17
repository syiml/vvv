package util.Games;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by QAQ on 2017/1/13.
 */
public class GamePlayerSocket implements IGamePlayer {

    private Socket socket;
    GamePlayerSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void setTimeOut(long millisecond) {

    }

    public int getInt() throws GameReadException{
        try {
            byte[] b = new byte[4];
            int len = socket.getInputStream().read(b);
            if(len!=4){
                throw new GameReadException();
            }
            return (b[0]<<24) + (b[1]<<16)+ (b[2]<<8)+ b[3];
        } catch (IOException e) {
            throw new GameReadException();
        }
    }
    public void putInt(int a) throws GameReadException{
        try {
            byte[] b = new byte[4];
            b[0] = (byte) (a >> 24);
            b[1] = (byte) (a >> 16);
            b[2] = (byte) (a >> 8);
            b[3] = (byte) (a);
            socket.getOutputStream().write(b);
        }catch (IOException e){
            throw new GameReadException();
        }
    }
}