package Main.contest.rank;

import Main.contest.Contest;
import Main.rating._rank;
import Main.status.statu;
import Tool.HTML.FromHTML.FormHTML;

/**
 * Created by Administrator on 2015/5/26.
 */
public abstract class Rank {
    public abstract String toHTML();
    public abstract void add(statu s,Contest c);//处理rejudge
    public abstract _rank get_rank();
    public abstract String getRuleHTML();
    //public abstract void init();
}
