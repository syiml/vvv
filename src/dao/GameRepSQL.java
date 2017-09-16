package dao;

import entity.GameRep;
import util.SQL.SQL;
import util.Tool;

import java.util.List;

/**
 * Created by QAQ on 2017/9/4.
 */
public class GameRepSQL extends BaseCacheLRU<Integer, GameRep> {

    private static GameRepSQL gameRepSQL = new GameRepSQL();
    private GameRepSQL() {
        super(10);
    }
    public static GameRepSQL getInstance(){
        return  gameRepSQL;
    }

    @Override
    protected GameRep getByKeyFromSQL(Integer key) {
        return new SQL("SELECT * FROM t_game_repetition WHERE id=?",key).queryBean(GameRep.class);
    }

    public void insert(GameRep rep){
        new SQL("INSERT t_game_repetition(blackId,blackAuthor,whiteId,whiteAuthor,processes,win,`time`) values(?,?,?,?,?,?,?)",
                rep.getBlackId(),rep.getBlackAuthor(),rep.getWhiteId(),rep.getWhiteAuthor(),rep.getProcesses().toString(),rep.getWin(), rep.getTime()).update();
    }

    public GameRep getAIBattleInfo(int id){
        return new SQL("SELECT * FROM t_game_repetition WHERE id=?",id).queryBean(GameRep.class);
    }
    public List<GameRep> getAIBattleInfo(int AiID, int from, int num){
        return new SQL("SELECT * FROM t_game_repetition WHERE blackId=? or whiteId=? ORDER BY id DESC LIMIT ?,?",AiID,AiID,from,num).queryBeanList(GameRep.class);
    }
    public int getAIBattleInfoNum(int AiID){
        return new SQL("SELECT count(*) FROM t_game_repetition WHERE blackId=? or whiteId=?",AiID,AiID).queryNum();
    }
}
