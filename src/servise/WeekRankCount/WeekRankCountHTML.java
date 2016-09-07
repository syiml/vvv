package servise.WeekRankCount;

import util.HTML.TableHTML;
import util.HTML.pageBean;
import util.Main;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by syimlzhu on 2016/9/7.
 */
public class WeekRankCountHTML extends pageBean {
    public static int pageSize = 50;

    private static WeekRankCount currWeekRank = null;
    private static WeekRankCount lastWeekRank = null;

    int from = 1;
    int num = 1;
    int page = 1;
    public WeekRankCountHTML(int page){
        if(currWeekRank == null){
            compute();
        }
        if(page<=0) page = 1;
        this.from = (page-1) * pageSize;
        this.num = from + pageSize;
        this.page = page;
        addTableHead("#","用户名","积分","1","2","3","4","5","6","7");
    }
    public static void compute(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Timestamp to = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.DATE,-7);
        Timestamp from = new Timestamp(calendar.getTimeInMillis());
        currWeekRank = new WeekRankCount();
        currWeekRank.compute(from , to);
        to = from;
        calendar.add(Calendar.DATE,-7);
        from = new Timestamp(calendar.getTimeInMillis());
        lastWeekRank = new WeekRankCount();
        lastWeekRank.compute(from , to);
    }

    @Override
    public String getTitle() {
        return "周活跃排行榜";
    }

    @Override
    public int getPageSize() {
        int totalCount = currWeekRank.size();
        if(totalCount < from+num){
            return Math.max(0,totalCount-from);
        }
        return num;
    }

    @Override
    public int getPageNum() {
        return getPageNum(currWeekRank.size(),pageSize);
    }

    @Override
    public int getNowPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        WeekRankRecord currRecord = currWeekRank.get(i);
        WeekRankRecord lastRecord = lastWeekRank.get(currRecord.username);
        if(colname.equals("#")){
            if(lastRecord == null){
                return currRecord.getRank()+"(new)";
            }else{
                int chg = currRecord.getRank() - lastRecord.getRank();
                if(chg == 0){
                    return currRecord.getRank()+"(-)";
                }else if(chg >0){
                    return currRecord.getRank()+"(+"+chg+")";
                }else{
                    return currRecord.getRank()+"("+chg+")";
                }
            }
        }
        if(colname.equals("用户名")){
            return Main.users.getUser(currRecord.username).getUsernameHTML();
        }
        if(colname.equals("积分")){
            int lastScore;
            if(lastRecord == null){
                lastScore = 0;
            }else{
                lastScore = currRecord.getScore();
            }
            int chg = currRecord.getScore() - lastScore;
            if(chg == 0){
                return currRecord.getScore()+"(-)";
            }else if(chg >0){
                return currRecord.getRank()+"(+"+chg+")";
            }else{
                return currRecord.getRank()+"("+chg+")";
            }
        }
        if(colname.equals("1")){
            return currRecord.getScore(1)+"";
        }
        if(colname.equals("2")){
            return currRecord.getScore(2)+"";
        }
        if(colname.equals("3")){
            return currRecord.getScore(3)+"";
        }
        if(colname.equals("4")){
            return currRecord.getScore(4)+"";
        }
        if(colname.equals("5")){
            return currRecord.getScore(5)+"";
        }
        if(colname.equals("6")){
            return currRecord.getScore(6)+"";
        }
        if(colname.equals("7")){
            return currRecord.getScore(7)+"";
        }
        return "=ERROR=";
    }

    @Override
    public String getLinkByPage(int page) {
        return "WeekRank.jsp?page="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }
}
