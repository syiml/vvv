package Challenge;

import Main.Main;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 负责给前台发送json数据
 * Created by Syiml on 2015/10/10 0010.
 */
public class ChallengeJSON {
    /**
     * @param user 查看的人
     * @return {blockList:[{id,name,group,score,isOpen,userScore},...],isSelf}
     */
    public static String getBlockList(String user){
        JSONObject ret=new JSONObject();
        ret.put("isSelf",user.equals(Main.loginUser().getUsername()));
        JSONArray blockList=new JSONArray();
        Map<Integer,Integer> userScore= ChallengeSQL.getUserScore(user);
        for(Integer blockId : ChallengeMain.blocks.keySet()){
            Block block= ChallengeMain.blocks.get(blockId);
            JSONObject aBlock=new JSONObject();
            aBlock.put("id",block.id);
            aBlock.put("name",block.name);
            aBlock.put("group",block.group);
            aBlock.put("score",block.score);
            aBlock.put("isOpen",block.isTrue(userScore));
            aBlock.put("userScore",userScore.get(blockId));
            blockList.add(aBlock);
        }
        ret.put("blockList",blockList);
        return ret.toString();
    }
}
