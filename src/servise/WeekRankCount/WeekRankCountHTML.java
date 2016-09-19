package servise.WeekRankCount;

import entity.Status;
import util.HTML.HTML;
import util.HTML.pageBean;
import util.Main;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by syimlzhu on 2016/9/7.
 */
public class WeekRankCountHTML extends pageBean {
    private static int pageSize = 50;

    //private static WeekRankCount currWeekRank = null;
    //private static WeekRankCount lastWeekRank = null;

    private static WeekRankCount[] weekRank = new WeekRankCount[8];

    private int from = 1;
    private int num = 1;
    private int page = 1;
    public WeekRankCountHTML(int page){
        if(weekRank[0] == null){
            compute();
        }
        if(page<=0) page = 1;
        this.from = (page-1) * pageSize;
        this.num = from + pageSize;
        this.page = page;
        addTableHead("Rank","用户名","积分","1天前","2天前","3天前","4天前","5天前","6天前");
    }
    public static void compute(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Timestamp getListTo = new Timestamp(calendar.getTimeInMillis());
        for(int i=0;i<weekRank.length;i++){
            Timestamp to = new Timestamp(calendar.getTimeInMillis());
            calendar.add(Calendar.DATE,-WeekRankCount.config.length);
            Timestamp from = new Timestamp(calendar.getTimeInMillis());
            weekRank[i] = new WeekRankCount();
            weekRank[i].compute(from , to);

            calendar.add(Calendar.DATE,WeekRankCount.config.length-1);
        }
        calendar.add(Calendar.DATE,-WeekRankCount.config.length+1);
        Timestamp getListFrom = new Timestamp(calendar.getTimeInMillis());

        List<Status> list = Main.status.getAcBetween(getListFrom,getListTo);
        Map<String,Set<Integer>> hash = new HashMap<>();
        for (Status status:list) {
            Set<Integer> set = hash.get(status.getUser());
            if(set == null){
                set = new HashSet<>();
                hash.put(status.getUser(),set);
            }
            if(set.contains(status.getPid())) continue;
            set.add(status.getPid());
            for (WeekRankCount rank:weekRank) {
                rank.addStatus(status);
            }
        }
        for (WeekRankCount rank:weekRank) rank.sort();
        for(int i=1;i<weekRank.length-1;i++){
            for(WeekRankRecord record: weekRank[i].resultsList){
                if(!weekRank[0].resultsMap.containsKey(record.username)){
                    weekRank[0].resultsMap.put(record.username,null);
                    WeekRankRecord newRecord = new WeekRankRecord();
                    newRecord.username = record.username;
                    newRecord.rank = -1;
                    weekRank[0].resultsList.add(newRecord);
                }
            }
        }
    }

    @Override
    public String getTitle() {
        return "周活跃排行榜";
    }

    @Override
    public int getCurrPageSize() {
        int totalCount = weekRank[0].size();
        if(totalCount < from+num){
            return Math.max(0,totalCount-from);
        }
        return num;
    }

    @Override
    public int getTotalPageNum() {
        return getTotalPageNum(weekRank[0].size(),pageSize);
    }

    @Override
    public int getCurrPage() {
        return page;
    }

    @Override
    public String getCellByHead(int i, String colname) {
        WeekRankRecord currRecord = weekRank[0].get((getCurrPage()-1)*pageSize+i);
        WeekRankRecord lastRecord = weekRank[1].get(currRecord.username);
        if(colname.equals("Rank")){
            if(lastRecord == null && currRecord.rank!=-1){
                return HTML.textb(currRecord.getRank()+"",4,"")+" "+HTML.textb("(new)","Green");
            }else if(lastRecord == null){
                return HTML.glyphicon("minus");
            }else if(currRecord.rank==-1){
                return HTML.glyphicon("minus");
            }else{
                int chg = currRecord.getRank() - lastRecord.getRank();
                if(chg == 0){
                    return HTML.textb(currRecord.getRank()+"",4,"");
                }else if(chg >0){
                    return HTML.textb(currRecord.getRank()+"",4,"")+" "+HTML.textb(HTML.glyphicon("arrow-down")+chg,"Red");
                }else{
                    return HTML.textb(currRecord.getRank()+"",4,"")+" "+HTML.textb(HTML.glyphicon("arrow-up")+-chg,"Green");
                }
            }
        }if(colname.equals("#")){
            return getRankShow(weekRank[0].get(currRecord.username),weekRank[1].get(currRecord.username));
        }
        if(colname.equals("用户名")){
            return Main.users.getUser(currRecord.username).getUsernameHTML();
        }
        if(colname.equals("积分")){
            int lastScore;
            if(lastRecord == null){
                lastScore = 0;
            }else{
                lastScore = lastRecord.getScore();
            }
            int chg = currRecord.getScore() - lastScore;
            if(chg == 0){
                return HTML.textb(currRecord.getScore()+"",getColorByRank(currRecord.getRank()));
            }else if(chg >0){
                return HTML.textb(currRecord.getScore()+"",getColorByRank(currRecord.getRank()))+HTML.textb("(+"+chg+")","Green");
            }else{
                return HTML.textb(currRecord.getScore()+"",getColorByRank(currRecord.getRank()))+HTML.textb("("+chg+")","Red");
            }
        }
        if(colname.equals("1天前")){
            return getRankShow(weekRank[1].get(currRecord.username),weekRank[2].get(currRecord.username));
        }
        if(colname.equals("2天前")){
            return getRankShow(weekRank[2].get(currRecord.username),weekRank[3].get(currRecord.username));
        }
        if(colname.equals("3天前")){
            return getRankShow(weekRank[3].get(currRecord.username),weekRank[4].get(currRecord.username));
        }
        if(colname.equals("4天前")){
            return getRankShow(weekRank[4].get(currRecord.username),weekRank[5].get(currRecord.username));
        }
        if(colname.equals("5天前")){
            return getRankShow(weekRank[5].get(currRecord.username),weekRank[6].get(currRecord.username));
        }
        if(colname.equals("6天前")){
            return getRankShow(weekRank[6].get(currRecord.username),weekRank[7].get(currRecord.username));
        }
        return ERROR_CELL_TEXT;
    }
    private String getColorByRank(int i){
        return "";
    }
    private String getRankShow(WeekRankRecord after,WeekRankRecord pre){
        if(after == null && pre == null){
            return "";
        }if(pre == null){
            return after.getRank()+" "+HTML.text("(new)",2,"Green");
        }else if(after == null){
            return HTML.glyphicon("minus");
        }else{
            int chg = after.getRank() - pre.getRank();
            if(chg == 0){
                return after.getRank()+"";
            }else if(chg >0){
                return after.getRank()+" "+HTML.text(HTML.glyphicon("arrow-down")+chg,2,"Red");
            }else{
                return after.getRank()+" "+HTML.text(HTML.glyphicon("arrow-up")+-chg,2,"Green");
            }
        }
    }

    @Override
    public String getLinkByPage(int page) {
        return "WeekRank.jsp?page="+page;
    }

    @Override
    public String rightForm() {
        return "";
    }

    public String IndexHTML(){
        Colname.clear();
        addTableHead("#","用户名");
        from = 0;
        num = 10;
        setCl("table table-condensed table-hover");
        return HTML.panelnobody("最近活跃排名"+HTML.floatRight(HTML.a("WeekRank.jsp","All")),tableHTML());
    }
}
