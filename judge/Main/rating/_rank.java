package Main.rating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/6/30 0030.
 */
public class _rank {
    List<Integer> rank;
    List<String> username;
    public _rank(){
        rank=new ArrayList<Integer>();
        username=new ArrayList<String>();
    }
    public void add(int r,String u){
        rank.add(r);
        username.add(u);
    }
}
