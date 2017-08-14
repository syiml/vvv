package action;

import dao.AiSQL;

/**
 * Created on 2017/8/6.
 */
public class AiAction extends BaseAction {
    private int id;//编号
    private String user;
    private int game_id;
    private String aiName;
    private String code;
    private String introduce; //说明

//    public String getAiInfoList(){
//             AiSQL.getAiInfoList(user);
//        return "success";


    public void setId(int id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public void setAiName(String aiName) {
        this.aiName = aiName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public int getGame_id() {
        return game_id;
    }

    public String getAiName() {
        return aiName;
    }

    public String getCode() {
        return code;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String addAiInfo(){
         return AiSQL.getInstance().addAiInfo(user,game_id,aiName,code,introduce);
    }

}
