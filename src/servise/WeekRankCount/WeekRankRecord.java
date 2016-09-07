package servise.WeekRankCount;

/**
 * Created by syimlzhu on 2016/9/7.
 */
public class WeekRankRecord implements Comparable<WeekRankRecord>{
    String username;
    int rank;
    int score;
    int scoreEveryDay[];
    int acNum[];

    WeekRankRecord(){
        scoreEveryDay = new int[7];
        acNum = new int[7];
    }

    public String getUsername() {
        return username;
    }

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }

    public int getScore(int i) {
        return scoreEveryDay[i];
    }

    public int getAcNum(int i) {
        return acNum[i];
    }

    void add(int day, int value){
        acNum[day]++;
        scoreEveryDay[day]+=value;
        score+=value;
    }


    @Override
    public int compareTo(WeekRankRecord o) {
        return score - o.score;
    }
}
