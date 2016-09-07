package servise.WeekRankCount;

import entity.Status;
import util.Main;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by syimlzhu on 2016/9/7.
 */
public class WeekRankCount {
    static int config[] = {1,2,3,4,5,6,7};
    Timestamp from;
    Timestamp to;
    Map<String,WeekRankRecord> resultsMap = new HashMap<>();
    List<WeekRankRecord> resultsList = new ArrayList<>();
    public void compute(Timestamp from,Timestamp to){
        this.from = from;
        this.to = to;
        List<Status> list = Main.status.getAcBetween(from,to);
        for (Status status:list) {
            addStatus(status);
        }

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
                record.rank = resultsList.get(i-1).rank+1;
            }
        }
    }
    public WeekRankRecord get(String username){
        return resultsMap.get(username);
    }
    public WeekRankRecord get(int i){
        return resultsList.get(i);
    }
    public int size(){return resultsList.size();}
    private void addStatus(Status s){
        WeekRankRecord record = resultsMap.get(s.getUser());
        if(record == null){
            record = new WeekRankRecord();
            resultsMap.put(s.getUser(),record);
        }
        int day = (int)((s.getSbmitTime().getTime() - from.getTime())/(1000*60*60*24));
        if(day<0 || day >=7) return ;
        record.add(day,config[day]);
    }
}
