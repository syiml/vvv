package entity;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public interface IBeanCanCach {
    public boolean isExpired();
    public void setExpired(Timestamp t);
}
