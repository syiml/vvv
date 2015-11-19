package Main.status;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Syiml on 2015/11/14 0014.
 */
public class StatusHtmlCache {
    Map<Integer,String> cache;
    public StatusHtmlCache(){
        cache=new HashMap<Integer, String>();
    }
    public void set(int cid,String html){
        cache.put(cid,html);
    }
    public String get(int cid){
        return cache.get(cid);
    }
}
