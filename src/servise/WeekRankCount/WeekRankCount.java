package servise.WeekRankCount;

import entity.Status;
import util.Main;
import util.Tool;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by syimlzhu on 2016/9/7.
 */
public class WeekRankCount {
    static int config[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    Timestamp from;
    Timestamp to;
    Map<String,WeekRankRecord> resultsMap = new HashMap<>();
    List<WeekRankRecord> resultsList = new ArrayList<>();
    public void compute(Timestamp from,Timestamp to){
        this.from = from;
        this.to = to;
    }
    public WeekRankRecord get(String username){
        return resultsMap.get(username);
    }
    public WeekRankRecord get(int i){
        return resultsList.get(i);
    }
    public int size(){return resultsList.size();}
    void addStatus(Status s){
        if(s.getSbmitTime().before(from))return;
        int day = (int)((s.getSbmitTime().getTime() - from.getTime())/(1000*60*60*24));
        if(day<0 || day >=WeekRankCount.config.length) return ;
        WeekRankRecord record = resultsMap.get(s.getUser());
        if(record == null){
            record = new WeekRankRecord();
            record.username = s.getUser();
            resultsMap.put(s.getUser(),record);
        }
        record.add(day,config[day]);
    }
    void sort(){
        for (WeekRankRecord record: resultsMap.values()) {
            resultsList.add(record);
        }
        Collections.sort(resultsList);
        for(int i = 0; i< resultsList.size(); i++) {
            WeekRankRecord record = resultsList.get(i);
            if(i==0){
                record.rank = 1;
            }else if(record.score == resultsList.get(i-1).score){
                record.rank = resultsList.get(i-1).rank;
            }else{
                record.rank = i+1;
            }
        }
    }
}
