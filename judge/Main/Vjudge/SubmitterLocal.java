package Main.Vjudge;

import Main.Main;
import Main.OJ.Local.Judge;
import Main.OJ.OTHOJ;
import Main.status.RES;
import Main.status.Result;

/**
 * Created by Syiml on 2015/10/14 0014.
 */
public class SubmitterLocal extends Submitter {

    public SubmitterLocal(int ojid, String us, String pas, int id, VJudge vj) {
        super(ojid, us, pas, id, vj);
    }

    public void go() {
        showstatus="go";
        this.status=BUSY;
        RES res=Judge.judge(info.pid,this);
        Main.status.setStatusResult(info.rid,res.getR(),res.getTime(),res.getMemory(),res.getCEInfo());
        this.status=IDLE;
    }
    public void run(){//开始执行线程
        try {
            while(true){
                //读取队列执行
                //System.out.println(submitterID+"IDLE");
                this.info=vj.localQueue.take();
                this.status=BUSY;
                Main.status.setStatusResult(info.rid,Result.JUDGING,"-","-","");
                go();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
