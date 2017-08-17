package util.Games;

import entity.Result;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import util.Games.GoBang.GameGoBangAIPlayer;
import util.MyClient;
import util.Tool;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jsoup.nodes.Document;

/**
 * Created by QAQ on 2017/8/11.
 */
public abstract class BaseJudgeSystemAIPlayer implements IGamePlayer {

    private static MyClient myClient = new MyClient();
    protected String ai_code;
    private long timeOut = 10000;
    public String game_id = "0";
    private BlockingQueue<Integer> catch_queue = new LinkedBlockingQueue<>();


    public BaseJudgeSystemAIPlayer(String ai_code){
        this.ai_code = ai_code;
    }

    @Override
    public void setTimeOut(long millisecond) {
        timeOut = millisecond;
    }

    @Override
    public int getInt() throws GameReadException {
        try {
            return catch_queue.poll(timeOut, TimeUnit.MILLISECONDS);
        } catch (NoSuchElementException | InterruptedException e) {
            throw new GameReadException();
        }
    }

    public boolean recvInt(int a){
        return catch_queue.offer(a);
    }

    public abstract void putResult(String res);

    @Override
    public abstract void putInt(int a) throws GameReadException ;


    public static void runAiCode(final GameGoBangAIPlayer ai, String input){
        List<NameValuePair> para = new ArrayList<>();
        para.add(new BasicNameValuePair("type","general"));
        para.add(new BasicNameValuePair("input",input));
        para.add(new BasicNameValuePair("rid",ai.game_id));
        para.add(new BasicNameValuePair("code",ai.ai_code));
        para.add(new BasicNameValuePair("timelimit",1000+""));
        para.add(new BasicNameValuePair("memorylimit",128+""));
        myClient.Post("http://106.15.197.254:8100",para);

        new Thread(){
            @Override
            public void run(){
                int times = 10;
                while(times-->0) {
                    Tool.sleep(1000);
                    List<NameValuePair> para = new ArrayList<>();
                    para.add(new BasicNameValuePair("type","getResult"));
                    para.add(new BasicNameValuePair("rid",ai.game_id));
                    String res = myClient.Post("http://106.15.197.254:8100/",para);
                    /*if(res!=null){//处理中文乱码
                        byte source [] = new byte[0];
                        try {
                            source = res.getBytes("iso8859-1");
                            res = new String (source,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }*/
                    //Tool.log(res);
                    JSONObject jo = JSONObject.fromObject(res);

                    if(jo.getString("ret").equals("success")){
                        String result = jo.getString("result");
                        JSONObject resultJson = JSONObject.fromObject(result);
                        if (!resultJson.getString("type").equals("padding") && !resultJson.getString("type").equals("judging")) {
                            ai.putResult(resultJson.getString("info"));
                            break;
                        }
                    }else if(jo.getString("ret").equals("noSubmit")){
                        ai.putResult("");
                    }
                }
            }
        }.start();
    }
}
