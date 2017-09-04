package util.Games.GoBang;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Games.BaseJudgeSystemAIPlayer;
import util.Games.GameReadException;
import util.Games.IGamePlayer;
import util.Tool;

import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by QAQ on 2017/8/6.
 */
public class GameGoBangAIPlayer extends BaseJudgeSystemAIPlayer {
    private GameGoBang gameGoBang;
    private String user;
    private boolean isBegin = false;
    private int col = -1,row = -1;
    private int id;

    public GameGoBangAIPlayer(int id,String ai_code,String user){
        super(ai_code);
        this.user = user;
        this.id = id;
    }

    public void setGameGoBang(GameGoBang game){
        this.gameGoBang = game;
    }

    @Override
    public void putResult(String res) {
        String[] ress = res.split(" ");
        if(ress.length !=2 ) return;
        try {
            ress[0] = ress[0].trim();
            ress[1] = ress[1].trim();
            int row = Integer.parseInt(ress[0]);
            int col = Integer.parseInt(ress[1]);
            recvInt(row);
            recvInt(col);
        }catch (NumberFormatException ignored){
        }
    }

    @Override
    public void putInt(int a) throws GameReadException {
        if(!isBegin){
            isBegin = true;
            if(a==1){
                StringBuilder input = new StringBuilder();
                JSONObject record = gameGoBang.getRecord();
                input.append(record.getInt("row")).append(" ").append(record.getInt("col")).append("\n");
                runAiCode(this,input.toString());
            }
        }else{
            if(col == -1) col = a;
            else if(row == -1){
                row = a;
                StringBuilder input = new StringBuilder();
                JSONObject record = gameGoBang.getRecord();
                input.append(record.getInt("row")).append(" ").append(record.getInt("col")).append("\n");
                JSONArray steps =  record.getJSONArray("step");
                for(int i=0;i<steps.size();i++){
                    JSONObject step = steps.getJSONObject(i);
                    input.append(step.getInt("x")).append(" ").append(step.getInt("y")).append("\n");
                }
                runAiCode(this,input.toString());
                row=col=-1;
            }
        }
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getAuthor() {
        return user;
    }

}
