package entity;

/**
 * Created by Administrator on 2015/6/7.
 */
public class RES {

    Result R;
    String Time;
    String Memory;
    String CEInfo;

    public RES(){
        R=Result.PENDDING;
        Time="-";
        Memory="-";
        CEInfo="";
    }
    public void setCEInfo(String CEInfo) {
        this.CEInfo = CEInfo;
    }
    public void setR(Result r) {
        this.R = r;
    }
    public void setTime(String time) {
        this.Time = time;
    }
    public void setMemory(String memory) {
        Memory = memory;
    }
    public boolean canReturn(){
        return R!=Result.PENDDING&&R!=Result.JUDGING&&R!=Result.RUNNING;
    }

    public String getCEInfo() {
        return CEInfo;
    }
    public Result getR() {
        return R;
    }
    public String getTime() {
        return Time;
    }
    public String getMemory() {
        return Memory;
    }
}
