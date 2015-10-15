package Main.Vjudge;

import Main.Main;
import com.google.gson.JsonArray;
import net.sf.json.JSONArray;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 处理机
 * Created by Administrator on 2015/5/21.
 */
public class VJudge {
    List<Submitter> s=new ArrayList<Submitter>();
    List<BlockingQueue<SubmitInfo>> queue = new ArrayList<BlockingQueue<SubmitInfo> >();
    BlockingQueue<SubmitInfo> localQueue = new LinkedBlockingQueue<SubmitInfo>();
    public VJudge(){//初始化。从文件读取？？从数据库？？
        //System.out.println("create Main!!!");

        s.add(new SubmitterLocal(0,"","",-1,this));
        s.add(new SubmitterLocal(0,"","",-1,this));

        String ss[]={"hdu","bnuoj","nbut","pku","hust","cf"};
        for(int j=0;j<ss.length;j++){
            String sss=ss[j];
            JSONArray GA=Main.GV.getJSONObject(sss).getJSONArray("Submitter");
            for(int i=0;i<GA.size();i++){
                JSONArray GGA=GA.getJSONArray(i);
                s.add(new Submitter(j, GGA.getString(0), GGA.getString(1), j*10+i,this));
            }
            queue.add(new LinkedBlockingQueue<SubmitInfo>());
        }

        DO();
    }
    public int addSubmit(SubmitInfo info,int oj){
        try {
            //System.out.println("Vjudge.addSubmit");
            queue.get(oj).put(info);
            //System.out.println("Vjudge.addSubmit Done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public int addSubmit(SubmitInfo info){
        try {
            localQueue.put(info);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
    private void DO(){
        for (Submitter ss : s) {
            Thread t1 = new Thread(ss);
            t1.start();
        }
    }
    public List<Submitter> getSubmitters(){
        return s;
    }
}
