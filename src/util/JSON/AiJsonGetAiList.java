package util.JSON;

import dao.AiSQL;
import entity.AiInfo;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by 无乐园 on 2017/8/22.
 */
public class AiJsonGetAiList extends BaseJsonPageBean<AiInfo>{

    public int game_id;

    public AiJsonGetAiList(int game_id,int nowPage, int everyPageNum) {
        super(nowPage, everyPageNum);
        this.game_id = game_id;
    }

    @Override
    protected List<AiInfo> getList(int from, int num) {
        return AiSQL.getInstance().getAiListRank(game_id, from, num);
    }

    protected List<AiInfo> getListRank(int game_id,int from, int num) {
        return AiSQL.getInstance().getAiListRank(game_id, from, num);
    }


    @Override
    protected int getTotalNum() {
        return AiSQL.getInstance().getTotalNumOfRank(game_id);
    }

    protected int getTotalNumOfRank(int game_id) {
        return AiSQL.getInstance().getTotalNumOfRank(game_id);
    }

    protected JSONObject processingData(JSONObject jo){
        int id = jo.getInt("id");
        jo.put("win",AiSQL.getInstance().getAiNumOfWin(id));
        jo.put("total",AiSQL.getInstance().getAiNumOfTotal(id));
        return jo;
    }

}
