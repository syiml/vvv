package entity.Title;

import entity.IBeanResultSetCreate;
import net.sf.json.JSONArray;
import util.Tool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 称号集合
 * Created by QAQ on 2017/5/27.
 */
public class TitleSet implements IBeanResultSetCreate{
    public Map<Integer,Timestamp> titles;

    public Map<Integer,Integer> order;

    public boolean isShow = true;

    public TitleSet(){
        titles = new HashMap<>();
        order = new HashMap<>();
    }

    public synchronized void addTitle(Integer id,Timestamp time){
        titles.put(id,time);
    }

    public synchronized boolean haveTitle(int id) {
        if (!titles.containsKey(id)) return false;
        Timestamp time = titles.get(id);
        if(time == null) return true;
        if(time.before(Tool.now())){
            titles.remove(id);
        }
        return true;
    }

    @Override
    public synchronized void init(ResultSet rs) throws SQLException {
        do{
            titles.put(rs.getInt("id"),rs.getTimestamp("endtime"));
        }while(rs.next());
    }

    public synchronized List<Integer> getShowTitles(){
        ArrayList<Integer> order_list = new ArrayList<>(100);
        for(int title_id: order.keySet()){
            int z = order.get(title_id);
            if(z!=-1) {
                for(int k=order_list.size();k<=z;k++){
                    order_list.add(0);
                }
                order_list.set(z, title_id);
            }
        }
        for(int title_id: BaseTitle.titles.keySet()){
            if(!order.containsKey(title_id)){
                order_list.add(title_id);
            }
        }
        order_list.add(-1);
        for(int title_id: order.keySet()){
            int z = order.get(title_id);
            if(z==-1) {
                order_list.add(title_id);
            }
        }
        order_list.remove((Integer)0);
        return order_list;
    }

    public synchronized int getShowTitle(){
        if(!isShow) return -1;
        List<Integer> ord = getShowTitles();
        for(Integer title_id:ord){
            if(haveTitle(title_id)) {
                if (order.containsKey(title_id)) {
                    int k = order.get(title_id);
                    if (k == -1) return -1;
                    else return title_id;
                }else
                    return title_id;
            }
        }
        return -1;
    }

    public synchronized String getOrder(){
        return JSONArray.fromObject(getShowTitles()).toString();
    }
    public synchronized void setOrder(String s){
        JSONArray ja = JSONArray.fromObject(s);
        int k = 0;
        for(int i=0;i<ja.size();i++){
            if(ja.getInt(i) == -1){
                k = 1;
            }
            else{
                if(k==1)
                    order.put(ja.getInt(i) ,-1);
                else
                    order.put(ja.getInt(i) ,i);
            }
        }
    }

    public synchronized void swap(int i,int j){
        int order1 = order.get(i);
        int order2 = order.get(j);
        order.put(i,order2);
        order.put(j,order1);
    }
    public synchronized void show(int i){
        List<Integer> showTitles = getShowTitles();
        int z = showTitles.indexOf(-1);
        order.put(i,z);
        setOrder(getOrder());
    }
    public synchronized void hide(int i){
        order.put(i,-1);
        setOrder(getOrder());
    }
}
