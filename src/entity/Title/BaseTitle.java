package entity.Title;

import entity.Title.AllCondition.BaseCondition;
import entity.Title.AllIntCompute.BaseIntCompute;
import entity.Title.AllTimeCompute.BaseTimeCompute;
import net.sf.json.JSONObject;
import servise.UserService;
import util.Event.BaseTitleEvent;
import util.Event.EventDeal;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.TreeMap;

import util.Event.EventMain;
import util.Event.Events.*;
import util.GlobalVariables.GlobalVariables;
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
    private BaseTimeCompute timeCompute,get_timeCompute;
    private String border_color = null;
    private String style = null;
    private int total_jd;
    private BaseIntCompute intCompute;

    protected BaseTitle(Class<T> cls) {
        super(cls);
    }

    public Integer getID(){return id;}

    public String getName(){return name;}
    public String getDes(){return des;}
    public int getTotal_jd(){return total_jd;}
    @Override
    public void dealEvent(T event) {
        if(lose_condition.check(event)){
            UserService.addTitle(event.user,getID(), 0,Tool.now());
        }
        if(condition.check(event)){
            long time;
            int jd = event.user.titleSet.getTitleJd(getID());
            int to_jd = intCompute.getInt(event,jd);
            if(to_jd >= total_jd){
                time = get_timeCompute.getTime(event);
            }else {
                time = timeCompute.getTime(event);
            }
            Timestamp timestamp;
            if(time <=0) timestamp = null;
            else timestamp = new Timestamp(time);
            UserService.addTitle(event.user,getID(),to_jd, timestamp);
        }
    }

    public String getBorder_color() {
        return border_color;
    }

    public String getStyle() {
        return style;
    }

    /////static//////
    public static void Init(){
        titles = new TreeMap<>();
        read();
        //添加title列表
    }
    public static TreeMap<Integer,BaseTitle> titles;
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

    /**
     * 从json配置中读取称号
     * @param jo
     * @return
     */
    public static BaseTitle getTitle(JSONObject jo){
        BaseTitle title;
        switch (jo.getString("event")){
            case "none":        title = new BaseTitle<>(BaseTitleEvent.class);  break;
            case "register":    title = new BaseTitle<>(EventRegister.class);   break;
            case "judge":       title = new BaseTitle<>(EventJudge.class);      break;
            case "verify":      title = new BaseTitle<>(EventVerify.class);     break;
            case "rating":      title = new BaseTitle<>(EventRating.class);     break;
            case "clock_in":    title = new BaseTitle<>(EventClockIn.class);    break;
            case "acb":         title = new BaseTitle<>(EventAcbChg.class);     break;
            case "rich_rank":   title = new BaseTitle<>(EventRichRank.class);   break;
            case "award":       title = new BaseTitle<>(EventAward.class);      break;
            case "rating_rank": title = new BaseTitle<>(EventRatingRank.class); break;
            default:{
                Tool.log("不存在"+jo.getString("event")+"事件");
                return null;
            }
        }
        title.id = jo.getInt("id");
        title.name = jo.getString("name");
        title.des = jo.getString("des");
        if(jo.containsKey("border_color")) title.border_color = jo.getString("border_color");
        if(jo.containsKey("style")) title.style = jo.getString("style");
        title.condition = BaseCondition.getCondition(jo.getJSONObject("condition"));
        title.condition.setTitleID(title.id);
        title.lose_condition = BaseCondition.getCondition(jo.getJSONObject("lose_condition"));
        title.lose_condition.setTitleID(title.id);
        title.timeCompute = BaseTimeCompute.getTimeCompute(jo.get("end_time"));
        title.timeCompute.setTitle_id(title.id);
        if(jo.containsKey("get_end_time")){
            title.get_timeCompute =  BaseTimeCompute.getTimeCompute(jo.get("get_end_time"));
            title.get_timeCompute.setTitle_id(title.id);
        }else{
            title.get_timeCompute = title.timeCompute;
        }
        title.intCompute = BaseIntCompute.getIntCompute(jo.get("jd_value"));
        title.total_jd = jo.getInt("jd");
        return title;
    }
}
