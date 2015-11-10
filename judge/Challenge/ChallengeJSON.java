package Challenge;

import Main.Main;
import Message.MessageSQL;
import Tool.SQL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

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
        if(user==null||user.equals("")) user=Main.loginUser().getUsername();
        JSONObject ret=new JSONObject();
        ret.put("isSelf",Main.loginUser()!=null&&Main.loginUser().getUsername().equals(user));
        ret.put("user",Main.users.getUser(user).getUsernameHTML());
        JSONArray blockList=new JSONArray();
        Map<Integer,Integer> userScore= ChallengeSQL.getUserScore(user);
        boolean ok=false;
        while(!ok){
            Set<Integer> openBlocks= ChallengeSQL.getOpenBlocks(user);
            blockList.clear();
            ok=true;
            for(Integer blockId : ChallengeMain.blocks.keySet()){
                Block block= ChallengeMain.blocks.get(blockId);
                JSONObject aBlock=new JSONObject();
                aBlock.put("id",blockId);
                aBlock.put("name",block.name);
                aBlock.put("group",block.group);
                aBlock.put("score",block.score);
                int z=0;//0、关闭  1、先决已经开启 2、可开启 3、已开启
                if(openBlocks.contains(blockId)) z=3;//本模块已经开启
                else if(block.pBlockOpen(openBlocks)){//先决已经开启
                    if(block.isTrue(userScore)){//模块的先决已经开启，且满足条件，可开启
                        ChallengeSQL.addOpenBlock(user,blockId);
                        Main.users.addACB(user, 30);
                        MessageSQL.addMessageBlockOpen(user,block.getName(),30);
                        ok=false;
                        break;//返回重新计算
                    }
                    else z=1;//先决开启但不满足条件
                }else z=0;//先决未开启
                aBlock.put("isOpen",z);
                if(userScore.containsKey(blockId)){
                    aBlock.put("userScore",userScore.get(blockId));
                }else{
                    aBlock.put("userScore", 0);
                }
                blockList.add(aBlock);
            }
        }

        ret.put("blockList",blockList);
        return ret.toString();
    }

    /**
     * @param id blockid
     * @param user 用户
     * @return {id,name,group,text,score,userScore,conditions:[{type,par,blockName,num}],problemList:[solved,pid,tpid,title,score]}
     */
    public static String getBlock(int id,String user){
        JSONObject ret=new JSONObject();
        Block block= ChallengeMain.blocks.get(id);
        ret.put("id",id);
        ret.put("name",block.name);
        ret.put("group",block.group);
        ret.put("text",ChallengeSQL.getText(id));
        ret.put("score",block.score);
        ret.put("userScore",ChallengeSQL.getUserScore(user,id));
        JSONArray conditions=new JSONArray();
        for(Condition c:block.conditions){
            JSONObject aCondition=new JSONObject();
            aCondition.put("type", c.type);
            aCondition.put("par",c.par);
            aCondition.put("blockName",ChallengeMain.blocks.get(c.par).name);
            aCondition.put("num",c.num);
            conditions.add(aCondition);
        }
        ret.put("conditions",conditions);
        JSONArray problemList=new JSONArray();
        ResultSet ps= ChallengeSQL.getProblems(user,id);
        try {
            while(ps.next()){
                JSONObject aProblem=new JSONObject();
                aProblem.put("solved",ps.getInt("solved"));
                aProblem.put("pid",ps.getInt("pid"));
                aProblem.put("tpid",ps.getInt("tpid"));
                aProblem.put("title",ps.getString("title"));
                aProblem.put("score",ps.getString("score"));
                problemList.add(aProblem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ret.put("problemList",problemList);
        return ret.toString();
    }
}
