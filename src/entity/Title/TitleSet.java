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

public class TitleSet implements IBeanResultSetCreate{
    public Map<Integer, title_value > titles;

    public Map<Integer,Integer> order;

    public boolean isShow = true;

    public int adj=-1,n=-1;//展示称号 id

    public TitleSet(){
        titles = new HashMap<>();
        order = new HashMap<>();
        adj=n=-1;
    }

    public synchronized void addTitle(Integer id,int jd,Timestamp time){
        titles.put(id,new title_value(jd,time));
    }

    public synchronized title_value getTitleValue(int id){
        if(titles.containsKey(id)){
            return titles.get(id);
        }
        return new title_value(0,null);
    }
    public synchronized int getTitleJd(int id){
        if(!titles.containsKey(id)) return 0;
        title_value value = titles.get(id);
        Timestamp time = value.clear_time;
        if(time == null){
            return value.jd;
        }
        if(time.before(Tool.now())){
            titles.remove(id);
            return 0;
        }
        return value.jd;
    }
    public synchronized boolean haveTitle(int id) {
        if(id==-1) return false;
        BaseTitle title = BaseTitle.getTitleByID(id);
        return getTitleJd(id) >= title.getTotal_jd();
    }

    @Override
    public synchronized void init(ResultSet rs) throws SQLException {
        do{
            titles.put(rs.getInt("id"),new title_value(rs.getInt("jd"),rs.getTimestamp("endtime")));
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
    public synchronized void top(int id){
        List<Integer> showTitles = getShowTitles();
        int i = showTitles.indexOf(id);
        for(int j=i;j>0;j--){
            showTitles.set(j,showTitles.get(j-1));
        }
        showTitles.set(0,id);
        setOrder(JSONArray.fromObject(showTitles).toString());
    }
    public synchronized void hide(int i){
        order.put(i,-1);
        setOrder(getOrder());
    }
    public String getNickTitle(){
        String ret = "";
        if(adj!=-1 && haveTitle(adj)){
            BaseTitle title_adj = BaseTitle.getTitleByID(adj);
            if(title_adj!=null) {
                ret += title_adj.getName() + "的";
            }
        }
        if(n!=-1 && haveTitle(n)){
            BaseTitle title_n = BaseTitle.getTitleByID(n);
            if(title_n!=null) {
                ret += title_n.getName();
            }
        }
        return ret.length()==0?"":"["+ret+"]";
    }
}
