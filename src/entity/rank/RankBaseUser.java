package entity.rank;

import java.util.Comparator;

/**
 * Created by QAQ on 2016/9/27.
 */
public abstract class RankBaseUser implements Comparable<RankBaseUser>{
    public String username;
    public int valid;
    public String showUsername;
    public String showNick;

    @Override
    public abstract int compareTo(RankBaseUser o);

    public void init(String username,int valid,int pid){
        this.username = username;
        this.valid = valid;
    }
    protected abstract boolean noRank();
}
