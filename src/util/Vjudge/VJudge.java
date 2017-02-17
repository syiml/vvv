package util.Vjudge;

import util.Main;
import net.sf.json.JSONArray;
import util.Tool;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 处理机
 * Created by Administrator on 2015/5/21.
 */
public class VJudge {
    List<VjSubmitter> s=new ArrayList<VjSubmitter>();
    List<BlockingQueue<SubmitInfo>> queue = new ArrayList<BlockingQueue<SubmitInfo> >();
    BlockingQueue<SubmitInfo> localQueue = new LinkedBlockingQueue<SubmitInfo>();
    public VJudge(){//初始化
        //System.out.println("create Main!!!");
        for(int i=0;i<Main.config.LocalJudgeNumber;i++){
            s.add(new SubmitterLocal(0,"","",-1,this));
        }

        String ss[]={"hdu","bnuoj","nbut","pku","hust","cf","codevs","judge_system","acdream"};
        for(int j=0;j<ss.length;j++){
            String sss=ss[j];
            JSONArray GA=Main.GV.getJSONObject(sss).getJSONArray("Submitter");
            for(int i=0;i<GA.size();i++){
                JSONArray GGA=GA.getJSONArray(i);
                s.add(new VjSubmitter(j, GGA.getString(0), GGA.getString(1), j*10+i,this));
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
            Tool.log(e);
        }
        return 1;
    }
    public int addSubmit(SubmitInfo info){
        try {
            localQueue.put(info);
        } catch (InterruptedException e) {
            Tool.log(e);
        }
        return 1;
    }
    private void DO(){
        for (VjSubmitter ss : s) {
            Thread t1 = new Thread(ss);
            ss.setSelfThread(t1);
            t1.start();
        }
    }
    public List<VjSubmitter> getSubmitters(){
        return s;
    }
}
