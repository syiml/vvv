package dao;

import entity.AiInfo;
import util.SQL.SQL;

import java.util.List;

/**
 * Created on 2017/8/6.
 */
public class AiSQL {

    public static List<AiInfo> getAiInfoList(String username){
        return new SQL("SELECT * FROM t_ai_info WHERE username= ?",username).queryBeanList(AiInfo.class);
    }

    public static String addAiInfo(String username,int game_id,String aiName,String code,String introduce){
        if (new SQL("INSERT INTO t_ai_info VALUES(?,?,?,?,?,?)",0,username,game_id,aiName,code,introduce).update() !=0){
            return "success";
        }
        return "error";
    }


}
