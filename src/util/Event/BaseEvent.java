package util.Event;

import util.Tool;

import java.sql.Timestamp;

/**
 * Created by QAQ on 2016/8/27.
 */
public class BaseEvent {
    public Integer getInt(String name){
        try{
            return Integer.parseInt(name);
        }catch (NumberFormatException e){
            return null;
        }
    }
    public String getString(String name){
        return name;
    }
    public Timestamp getTimestamp(String name){
        return Tool.now();
    }

    public void before(){}
}
