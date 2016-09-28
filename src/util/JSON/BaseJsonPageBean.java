package util.JSON;

import entity.ICanToJSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * Created by QAQ on 2016/9/21.
 */
public abstract class BaseJsonPageBean<T extends ICanToJSON> {
    protected int nowPage;
    private int totalPage;
    private int everyPageNum;

    private List<T> list;
    protected BaseJsonPageBean(int nowPage,int everyPageNum){
        this.nowPage = nowPage;
        this.everyPageNum = everyPageNum;
    }

    public static int getTotalPageNum(int Num, int everyPageNum){
        if(Num==0) return 1;
        if(Num%everyPageNum==0){
            return Num/everyPageNum;
        }else return Num/everyPageNum+1;
    }

    abstract List<T> getList(int from,int num);

    abstract int getTotalNum();

    protected int getTotalPage(){
        return getTotalPageNum(getTotalNum(), everyPageNum);
    }

    protected List<T> getList(){
        return getList((nowPage-1)* everyPageNum, everyPageNum);
    }

    public JSONObject toJSON(){
        list = getList();
        totalPage = getTotalPage();
        JSONObject ret = new JSONObject();
        ret.put("nowPage",nowPage);
        ret.put("totalPage",totalPage);
        ret.put("everyPageNum",everyPageNum);
        JSONArray jo = new JSONArray();
        for(T t: list){
            jo.add(t.toJSON());
        }
        ret.put("data",jo);
        return ret;
    }
}
