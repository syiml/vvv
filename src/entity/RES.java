package entity;

/**
 * Created by Administrator on 2015/6/7.
 */
public class RES {

    private Result R;
    private String Time;
    private String Memory;
    private String CEInfo;
    private int score;

    public RES(){
        R=Result.PENDING;
        Time="-";
        Memory="-";
        CEInfo="";
        score = -1;
    }

    public boolean canReturn(){
        return R!=Result.PENDING &&R!=Result.JUDGING&&R!=Result.RUNNING;
    }

    public String getCEInfo() {
        return CEInfo;
    }

    public void setCEInfo(String CEInfo) {
        this.CEInfo = CEInfo;
    }

    public Result getR() {
        return R;
    }

    public void setR(Result r) {
        this.R = r;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        this.Time = time;
    }

    public String getMemory() {
        return Memory;
    }

    public void setMemory(String memory) {
        Memory = memory;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUseTime(){
        return Integer.parseInt(Time.substring(0, getTime().length()-2));
    }
}
