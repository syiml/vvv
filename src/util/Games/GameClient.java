package util.Games;

import util.Tool;

import java.io.*;
import java.net.Socket;

/**
 * Created by QAQ on 2017/1/14.
 */
public class GameClient {
    private static Socket socket;

    public static int getInt(InputStream inputStream) throws GameReadException {
        try {
            byte[] b = new byte[4];
            int len = inputStream.read(b);
            if (len != 4) {
                throw new GameReadException();
            }
            return (b[0] << 24) + (b[1] << 16) + (b[2] << 8) + b[3];
        } catch (IOException e) {
            throw new GameReadException();
        }
    }

    public static void putInt(OutputStream outputStream, int a) throws GameReadException {
        try {
            byte[] b = new byte[4];
            b[0] = (byte) (a >> 24);
            b[1] = (byte) (a >> 16);
            b[2] = (byte) (a >> 8);
            b[3] = (byte) (a);
            outputStream.write(b);
        } catch (IOException e) {
            throw new GameReadException();
        }
    }


    public static void main(String args[]) {
        try {
            socket = new Socket("127.0.0.1", 12306);
            socket.setSoTimeout(1);
            final Process runExe = Runtime.getRuntime().exec("E:\\syiml\\wzq\\cppClient\\cppClient\\bin\\Debug\\ai.exe");
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            int x = GameClient.getInt(socket.getInputStream());
                            Tool.debug("<=" + x);
                            runExe.getOutputStream().write((x + "\n").getBytes());
                            runExe.getOutputStream().flush();
                        } catch (GameReadException | IOException e) {
                            //e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void run() {
                    BufferedReader is = new BufferedReader(new InputStreamReader(runExe.getInputStream()));
                    while (true) {
                        try {
                            String isOut = is.readLine();
                            int x = Integer.parseInt(isOut);
                            Tool.debug("=>" + x);
                            GameClient.putInt(socket.getOutputStream(), x);
                        } catch (IOException | GameReadException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}