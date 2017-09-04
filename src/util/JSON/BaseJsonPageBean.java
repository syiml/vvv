package util.JSON;

import entity.ICanToJSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * 生成一个可用于翻页的json对象给客户端
 * Created by QAQ on 2016/9/21.
 */
public abstract class BaseJsonPageBean<T extends ICanToJSON> {
    protected int nowPage;//当前在第几页
    private int totalPage;//总页数
    private int everyPageNum;//每页的数量

    private List<T> list;

    /**
     * 构造函数，初始化参数
     * @param nowPage 当前页码
     * @param everyPageNum 每页数量
     */
    protected BaseJsonPageBean(int nowPage,int everyPageNum){
        this.nowPage = nowPage;
        this.everyPageNum = everyPageNum;
    }

    /**
     * 计算总页数
     * @param Num 总数量
     * @param everyPageNum 每页的数量
     * @return 总页数
     */
    public static int getTotalPageNum(int Num, int everyPageNum){
        if(Num==0) return 1;
        if(Num%everyPageNum==0){
            return Num/everyPageNum;
        }else return Num/everyPageNum+1;
    }

    /**
     * 重写这个方法，获取实体类的列表（如从mysql里面读取，可以直接使用limit）
     * @param from 从第from个开始取
     * @param num 取num个
     * @return 实体类列表
     */
    protected abstract List<T> getList(int from, int num);

    /**
     * 重写这个方法，返回的是所有实体类的总数量
     * @return 返回总数量
     */
    protected abstract int getTotalNum();

    protected int getTotalPage(){
        return getTotalPageNum(getTotalNum(), everyPageNum);
    }

    protected List<T> getList(){
        if(nowPage<=0) nowPage=1;
        return getList((nowPage-1)* everyPageNum, everyPageNum);
    }

    /**
     * 修改最后返回的json对象
     * @param jo 修改前
     * @return 修改后
     */
    protected JSONObject processing(JSONObject jo){
        return jo;
    }

    /**
     * 修改实体类。如果有必要的话重写
     * @param jo 修改前
     * @return 修改后
     */
    protected JSONObject processingData(JSONObject jo){
        return jo;
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
            jo.add(processingData(t.toJSON()));
        }
        ret.put("data",jo);
        processing(ret);
        return ret;
    }
}
