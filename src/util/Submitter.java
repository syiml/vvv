package util;

import entity.OJ.BNUOJ.BNUOJ;
import entity.OJ.CF.CF;
import entity.OJ.HDU.HDU;
import entity.OJ.HUST.HUST;
import entity.OJ.NBUT.NBUT;
import entity.OJ.OTHOJ;
import entity.OJ.PKU.PKU;
import entity.statu;
import util.Vjudge.VJudge;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/12/14 0014.
 */
public interface Submitter {
    OTHOJ[] ojs ={new HDU(),new BNUOJ(),new NBUT(),new PKU(),new HUST(),new CF()};
    //OJ列表。判题OJ顺序不能改变，否则导致已有题目的OJ不正确
    VJudge m=new VJudge();

    int doSubmit(String user,int pid,int cid,int language,String code,Timestamp submittime);
    void onSubmitDone(statu s);
    int reJudge(int rid);
}
