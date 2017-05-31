package util.Event;

import util.Tool;

import java.sql.Timestamp;

/**
 * 事件基类
 * Created by QAQ on 2016/8/27.
 */
public class BaseEvent {
    /**
     * 从触发的事件中读取一个int属性
     * @param name 属性名
     * @return 属性值
     */
    public Integer getInt(String name){
        try{
            return Integer.parseInt(name);
        }catch (NumberFormatException e){
            return null;
        }
    }

    /**
     * 从出发的事件中读取一个字符串型的属性
     * @param name 属性名
     * @return 属性值
     */
    public String getString(String name){
        return name;
    }

    /**
     * 从出发的事件中读取一个时间戳类型的属性
     * @param name 属性名
     * @return 属性值
     */
    public Timestamp getTimestamp(String name){
        return Tool.now();
    }

    /**
     * 子类重写这个方法，在处理事件之前调用
     */
    public void before(){}
}
