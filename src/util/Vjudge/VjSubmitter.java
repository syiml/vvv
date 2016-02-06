package util.Vjudge;

import util.Main;
import entity.OJ.OTHOJ;
import entity.RES;
import entity.Result;
import util.Submitter;
import util.Tool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/21.
 */
/*
* 提交器
* */
public class VjSubmitter implements Runnable{
    SubmitInfo info;//正在处理的info
    int status;//忙碌状态与否
    private String username;
    private String password;
    int ojid;
    private String ojsrid;
    int submitterID;
    public String showstatus="";

    VJudge vj;

    public VjSubmitter(int ojid, String us, String pas, int id, VJudge vj){
        this.ojid=ojid;
        username=us;
        password=pas;
        status=IDLE;
        submitterID=id;
        this.vj=vj;
    }
    public SubmitInfo getSubmitInfo(){return info;}
    public int getOjid() {
        return ojid;
    }
    public boolean isBusy(){
        return status==BUSY;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getOjsrid(){return ojsrid;}
    public void setShowstatus(String status){
        this.showstatus=status;
    }

    public void run(){//开始执行线程
        while(true){
            try {
                //读取队列执行
                //System.out.println(submitterID+"IDLE");
                this.info=vj.queue.get(ojid).take();
                this.status=BUSY;
                Main.status.setStatusResult(info.rid,Result.JUDGING,"-","-","");
                go();
            } catch (Exception e) {
                this.status=IDLE;
                e.printStackTrace();
                setShowstatus("出错，10秒后重新运行");
                Tool.sleep(10000);
            }
        }
    }
    public void go() {
        try{
            //System.out.println(submitterID+":submitter go");
            setShowstatus("开始执行，正在第1次获取原rid");
            OTHOJ oj;
            oj = Submitter.ojs[ojid];
            String prid;
            int z=0;
            do{
                z++;
                if(z>=10){
                    //System.out.println(submitterID+":getPrid Error");
                    Main.status.setStatusResult(info.rid, Result.ERROR, "-", "-", "");
                    this.status=IDLE;
                    setShowstatus("获取原rid出错");
                    return;
                }
                prid = oj.getRid(username);//获得原来的rid
                setShowstatus("第"+z+"次获取的原rid="+prid);
            }while(prid.equals("error"));
            setShowstatus("第"+z+"次获取的原rid="+prid+",开始提交");
            String nrid;
            RES r ;
            int num = 0;
            int k=2;
            do{
                while (oj.submit(this).equals(submitterID+":error")) {
                    num++;
                    if (num >= 10) {
                        //System.out.println(submitterID+":doSubmit out time");
                        Main.status.setStatusResult(info.rid, Result.ERROR, "-", "-", "");
                        this.status=IDLE;
                        setShowstatus("第"+num+"次的提交出错，评测出错");
                        return;
                    }else{
                        setShowstatus("第"+num+"次的提交出错，开始重试");
                    }
                    Tool.sleep(1000);
                }
                setShowstatus("提交结束，开始获取评测结果");
                num = 0;
                do {
                    Tool.sleep(1000);
                    nrid = oj.getRid(username);
                    //System.out.println(submitterID+":get rid "+num+"=" + nrid);
                    setShowstatus("第"+num+"次获取rid="+nrid);
                    num++;
                    if (num == 10) break;//提交失败重新提交
                } while (nrid.equals(prid));
                k--;
            }while(num==10&&k!=0);
            if(k==0){
                Main.status.setStatusResult(info.rid, Result.ERROR, "-", "-", "");
                setShowstatus("提交失败");
            }else{
                ojsrid = nrid;
                do{
                    Tool.sleep(1000);
                    r=oj.getResult(this);
                    //System.out.println(submitterID+":get res="+r.getR());
                    setShowstatus("评测结果="+nrid);
                }while(!r.canReturn());
                Main.submitter.onSubmitDone(Main.status.setStatusResult(info.rid, r.getR(),r.getTime(),r.getMemory(),r.getCEInfo()));
            }
            this.status=IDLE;
        }catch(Exception e){
            setShowstatus("未知错误");
            e.printStackTrace();
            Main.status.setStatusResult(info.rid, Result.ERROR, "-", "-", "");
            this.status=IDLE;
        }
    }

    public List<String> row(){
        //submitterID,ojid,status,username,info.rid,info.pid,ojsrid,show
        List<String> row=new ArrayList<String>();
        row.add(submitterID+"");
        row.add(ojid+"");
        row.add(status==1?"正在评测":"空闲");
        row.add(username);
        row.add(info==null?"":info.rid+"");
        row.add(info==null?"":info.pid+"");
        row.add(ojsrid+"");
        row.add(showstatus);
        return row;
    }

    public static final int BUSY=1;
    public static final int IDLE=0;
}
