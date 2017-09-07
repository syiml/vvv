package action;

import dao.AiSQL;
import entity.User;
import util.JSON.AiJsonGetAiList;
import util.Main;

/**
 * Created on 2017/8/6.
 */
public class AiAction extends BaseAction {
    private int id;//编号
    private String username;
    private int game_id;
    private String aiName;
    private String code;
    private String introduce; //说明
    private int page;//当前页面

    private String jsonpCallback;//服务端用于接收callback调用的function名的参数

    public void setId(int id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJsonpCallback() {
        return jsonpCallback;
    }

    public void setJsonpCallback(String jsonpCallback) {
        this.jsonpCallback = jsonpCallback;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String addAiInfo(){
        User u= Main.loginUser();
        if (u == null){
            return "login";
        }
        String buff = AiSQL.getInstance().addAiInfo(u.getUsername(),game_id,aiName,code,introduce);
        return buff;
    }

    public String updateAiInfo(){
        User u= Main.loginUser();
        if (u == null){
            return "login";
        }
        String buff = AiSQL.getInstance().updateAiInfo(id,aiName,code,introduce);
        return buff;
    }

    public String getAiInfoById(){out.print(jsonpCallback+"("+AiSQL.getInstance().getAiInfoById(id)+")");//不规范的jsonp调用
            //out.print(AiSQL.getInstance().getAiInfoById(id));//一般调用
        return NONE;
    }

    public String getAiListRank(){
        out.print(jsonpCallback+"("+new AiJsonGetAiList(game_id,page,10).toJSON().toString()+")");
        return NONE;
    }

    public String getAiListUser(){
        User u= Main.loginUser();
        if (aiName == null) aiName ="";
        out.print(jsonpCallback+"("+new AiJsonGetAiList(u.getUsername(),game_id,aiName,page,10).toJSON().toString()+")");
        return NONE;
    }

    public String getAboutLogin(){ //判断用户是否登陆.
        User u= Main.loginUser();
        if (u == null){
            out.print(jsonpCallback+"({\"ret\":\"error\"})");
        }else{
            out.print(jsonpCallback+"({\"ret\":\"success\"})");
        }
        return NONE;
    }

    public String getEditAiView(){
        out.print(AiSQL.getInstance().getEditAiView(id));
        return NONE;
    }




}
