package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 CREATE TABLE  `vjudge`.`log` (
 `id` INT NOT NULL AUTO_INCREMENT ,
 `time` DATETIME NOT NULL ,
 `text` TEXT NOT NULL ,
 `sessionUser` VARCHAR( 30 ) NULL ,
 PRIMARY KEY (  `id` )
 ) ENGINE = INNODB
 * Created by QAQ on 2016-2-27.
 */
public class Log implements IBeanResultSetCreate<Log>{
    private int id;
    private Timestamp time;
    private String text;
    private String sessionUser;

    public Log(){

    }
    public Log(Timestamp time,String text,String sessionUser){
        this.time=time;
        this.text=text;
        this.sessionUser=sessionUser;
    }
    @Override
    public Log init(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        time = rs.getTimestamp("time");
        text = rs.getString("text");
        sessionUser = rs.getString("sessionUser");
        return this;
    }

    public int getId() {
        return id;
    }

    public String getSessionUser() {
        return sessionUser;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTime() {
        return time;
    }
}
