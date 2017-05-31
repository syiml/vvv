package entity.Title;

import java.sql.Timestamp;

/**
 * 称号集合
 * Created by QAQ on 2017/5/27.
 */
public class title_value{
    title_value(int jd, Timestamp clear_time){
        this.jd= jd;
        this.clear_time = clear_time;
    }
    public int jd;
    public Timestamp clear_time;
}
