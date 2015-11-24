package entity.rank.RankShortCode;
import entity.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syiml on 2015/7/23 0023.
 */
public class user implements Comparable<user>{
    String username;
    boolean valid;
    int pnum;
    int chengfa;
    List<Integer> codelen;//0表示未过  >0表示通过的最短代码长度
    List<Integer> wrongtime;
    public user(String username,int pnum,boolean valid,int chengfa){
        this.username=username;
        this.pnum=pnum;
        this.valid=valid;
        codelen=new ArrayList<Integer>();
        wrongtime=new ArrayList<Integer>();
        for(int i=0;i<pnum;i++){
            codelen.add(0);
            wrongtime.add(0);
        }
    }
    public void addres(int pid,Result s,int len){
        if(s== Result.AC){
            if(codelen.get(pid)>0){
                codelen.set(pid,Math.min(codelen.get(pid),len));//取短
            }else{
                codelen.set(pid,len);
            }
        }else{
            wrongtime.set(pid,wrongtime.get(pid)+1);//错误一次
        }
    }

    public int getAcNum(){
        int ret=0;
        for(int i=0;i<pnum;i++){
            if(codelen.get(i)>0){
                ret++;
            }
        }
        return ret;
    }
    public int getCodelen(int chengfa){
        int ret=0;
        for(int i=0;i<pnum;i++){
            if(codelen.get(i)>0){
                ret+=codelen.get(i);
                ret+=wrongtime.get(i)*chengfa;
            }
        }
        return ret;
    }
    public int compareTo(user u){
        int ac=getAcNum();
        int uac=u.getAcNum();
        if(ac==uac){
            int codelen=getCodelen(chengfa);
            int ucodelen=u.getCodelen(chengfa);
            return codelen-ucodelen;
        }else{
            return uac-ac;
        }
    }
}
