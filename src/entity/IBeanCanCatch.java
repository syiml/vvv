package entity;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public interface IBeanCanCatch {
    Timestamp getExpired();
    void setExpired(Timestamp t);
}
