package util.Vjudge;

import util.Main;
import entity.OJ.LocalJudge.LocalJudge;
import entity.RES;
import entity.Result;
import util.Tool;

/**
 * Created by Syiml on 2015/10/14 0014.
 */
public class SubmitterLocal extends VjSubmitter {

    public SubmitterLocal(int ojid, String us, String pas, int id, VJudge vj) {
        super(ojid, us, pas, id, vj);
    }

    public void go() {
        showstatus="go";
        RES res= LocalJudge.judge(this);
        Main.submitter.onSubmitDone(Main.status.setStatusResult(info.rid,res.getR(),res.getTime(),res.getMemory(),res.getCEInfo()));
    }
    public void run(){//开始执行线程
        while(true){
            try {
                //读取队列执行
                //System.out.println(submitterID+"IDLE");
                this.info=vj.localQueue.take();
                Main.status.setStatusResult(info.rid,Result.JUDGING,"-","-","");
                this.status=BUSY;
                go();
                this.status=IDLE;
            } catch (Exception e){
                Tool.log("本地评测机错误");
                e.printStackTrace();
            } finally {
                this.status=IDLE;
            }
        }
    }
}
