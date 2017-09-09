package util.JSON;

import dao.AiSQL;
import entity.AiInfo;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created  on 2017/9/5.
 */
public class AiJsonGetAiList extends BaseJsonPageBean<AiInfo>{
    public int game_id;
    public String username;
    public String aiName;

    public AiJsonGetAiList(String username, int game_id, String aiName,int nowPage, int everyPageNum) {
        super(nowPage, everyPageNum);
        this.game_id = game_id;
        this.username = username;
        this.aiName = aiName;
    }

    public AiJsonGetAiList(int game_id, int nowPage, int everyPageNum) {
        super(nowPage, everyPageNum);
        this.game_id = game_id;
    }

    @Override
    protected List<AiInfo> getList(int from, int num) {
        if (username == null){ //获取Rank
            return AiSQL.getInstance().getAiListRank(game_id,from,num);
        }
        //获取用户所有的AI
        return AiSQL.getInstance().getAiListUser(username,aiName,game_id, from, num);
    }

    public List<AiInfo> jspGetList(){
        return getList();
    }

    @Override
    protected int getTotalNum() {
        if (username == null){
            return AiSQL.getInstance().getAiTotalNumOfRank(game_id);
        }
        return AiSQL.getInstance().getAiTotalNumByUser(username,aiName,game_id);
    }

    public int jspGetTotalPage(){
        return getTotalPage();
    }

    protected JSONObject processingData(JSONObject jo){
        int id = jo.getInt("id");
        jo.put("win",AiSQL.getInstance().getAiNumOfWin(id));
        jo.put("total",AiSQL.getInstance().getAiNumOfTotal(id));
        return jo;
    }

    @Override
    protected JSONObject processing(JSONObject jo){
        //jo.put("max_score",AiSQL.getInstance().getMaxScore(game_id));
        return jo;
    }
}
