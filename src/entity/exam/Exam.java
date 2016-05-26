package entity.exam;

import entity.IBeanCanCach;
import util.Tool;

import java.sql.Timestamp;
import java.util.List;

/**
 * 考试类
 * Created by QAQ on 2016/5/25 0025.
 */
public class Exam implements IBeanCanCach{
    List<Base_Exam_problem> problems;








    private Timestamp catch_time;
    @Override
    public boolean isExpired() {
        return catch_time.before(Tool.now());
    }
    @Override
    public void setExpired(Timestamp t) {
        catch_time = t;
    }
}
