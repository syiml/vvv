package util.Vjudge;

import util.Main;
import entity.OJ.LocalJudge.LocalJudge;
import entity.RES;
import entity.Result;

/**
 * Created by Syiml on 2015/10/14 0014.
 */
public class SubmitterLocal extends Submitter {

    public SubmitterLocal(int ojid, String us, String pas, int id, VJudge vj) {
        super(ojid, us, pas, id, vj);
    }

    public void go() {
        showstatus="go";
        RES res= LocalJudge.judge(this);
        Main.status.setStatusResult(info.rid,res.getR(),res.getTime(),res.getMemory(),res.getCEInfo());
    }
    public void run(){//开始执行线程
        try {
            while(true){
                //读取队列执行
                //System.out.println(submitterID+"IDLE");
                this.info=vj.localQueue.take();
                Main.status.setStatusResult(info.rid,Result.JUDGING,"-","-","");
                this.status=BUSY;
                go();
                this.status=IDLE;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
