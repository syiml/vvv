package entity.Title;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2017/5/27.
 */
public class Title {
    private int id;
    private Timestamp endTime;
    private boolean isHave;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }
}
