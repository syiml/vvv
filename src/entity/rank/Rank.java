package entity.rank;

import entity.Contest;
import util.rating._rank;
import entity.Status;

/**
 * Created by Administrator on 2015/5/26.
 */
public abstract class Rank {
    public abstract String toHTML();
    public abstract void add(Status s,Contest c);//处理rejudge
    public abstract _rank get_rank();
    public abstract String getRuleHTML();
    //public abstract void init();
}
