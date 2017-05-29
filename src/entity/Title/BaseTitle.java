package entity.Title;

import entity.Title.AllCondition.BaseCondition;
import entity.Title.AllTimeCompute.BaseTimeCompute;
import entity.Title.AllTitle.Title_MengNew;
import net.sf.json.JSONObject;
import servise.UserService;
import util.Event.BaseEvent;
import util.Event.BaseTitleEvent;
import util.Event.EventDeal;

import java.sql.Timestamp;
import java.util.HashMap;

import util.Event.EventMain;
import util.Event.Events.*;
import util.GlobalVariables.GlobalVariables;
import util.MyTime;
import util.Tool;

/**
 * 必须在EventMain.Init()里面调用 addEventDeal 来注册事件处理器
 * Created by QAQ on 2017/5/27.
 */
public class BaseTitle<T extends BaseTitleEvent> extends EventDeal<T>{
    private int id;
    private String name;
    private String des;
    private BaseCondition condition,lose_condition;
    private BaseTimeCompute timeCompute;
    private String border_color = null;

    protected BaseTitle(Class<T> cls) {
        super(cls);
    }

    public Integer getID(){return id;}

    public String getName(){return name;}
    public String getDes(){return des;}

    @Override
    public void dealEvent(T event) {
        if(lose_condition.check(event)){
            UserService.addTitle(event.user,getID(), Tool.now());
        }
        if(condition.check(event)){
            long time = timeCompute.getTime(event);
            Timestamp timestamp;
            if(time == -1) timestamp = null;
            else timestamp = new Timestamp(time);
            UserService.addTitle(event.user,getID(), timestamp);
        }
    }

    public String getBorder_color() {
        return border_color;
    }

    /////static//////
    public static void Init(){
        titles = new HashMap<>(20);
        read();
        //添加title列表
    }
    public static HashMap<Integer,BaseTitle> titles;
    private static void putTitle(BaseTitle ... args){
        for(BaseTitle title:args){
            titles.put(title.getID(),title);
            EventMain.addEventDeal(title);
        }
    }
    public static BaseTitle getTitleByID(int id){
        return titles.get(id);
    }
    public static void read(){
        String JsonContext = new GlobalVariables().ReadFile(BaseTitle.class.getResource("title.json").getPath());
        JSONObject jo = JSONObject.fromObject(JsonContext);
        for(int i=0;i<jo.getJSONArray("titles").size();i++){
            putTitle(getTitle(jo.getJSONArray("titles").getJSONObject(i)));
        }
    }
    public static BaseTitle getTitle(JSONObject jo){
        BaseTitle title;
        switch (jo.getString("event")){
            case "none":title = new BaseTitle<>(BaseTitleEvent.class);break;
            case "register": title = new BaseTitle<>(EventRegister.class); break;
            case "judge":title = new BaseTitle<>(EventJudge.class);break;
            case "verify":title = new BaseTitle<>(EventVerify.class);break;
            case "rating":title = new BaseTitle<>(EventRating.class);break;
            case "clock_in":title = new BaseTitle<>(EventClockIn.class);break;
            default:{
                Tool.log("不存在"+jo.getString("event")+"事件");
                return null;
            }
        }
        title.id = jo.getInt("id");
        title.name = jo.getString("name");
        title.des = jo.getString("des");
        if(jo.containsKey("border_color")) title.border_color = jo.getString("border_color");
        title.condition = BaseCondition.getCondition(jo.getJSONObject("condition"));
        title.lose_condition = BaseCondition.getCondition(jo.getJSONObject("lose_condition"));
        title.timeCompute = BaseTimeCompute.getTimeCompute(jo.getJSONObject("end_time"));
        return title;
    }
}
