package util.HTML;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * {nowpage,pagenum,islast,data[]}
 * Created by Syiml on 2015/11/15 0015.
 */
public class pageBeanJson {
    public void setNowpage(int nowpage) {
        this.nowpage = nowpage;
    }
    public void setPagenum(int pagenum) {
        this.pagenum = pagenum;
    }
    public void setIslast(boolean islast) {
        this.islast = islast;
    }
    public void setJa(JSONArray ja) {
        this.ja = ja;
    }

    int nowpage;
    int pagenum;
    boolean islast;
    JSONArray ja;

    public String toString(){
        JSONObject jo=new JSONObject();
        jo.put("nowpage",nowpage);
        jo.put("pagenum",nowpage);
        jo.put("islast",nowpage);
        jo.put("data",ja);
        return jo.toString();
    }
}
