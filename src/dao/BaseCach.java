package dao;

import entity.IBeanCanCach;
import entity.User;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public abstract class BaseCach<K,T extends IBeanCanCach> {
    private ConcurrentHashMap<K,T> _catch = new ConcurrentHashMap<>();
    protected long cachTime = 5*60*1000;//默认5分钟

    protected T getBeanFromCatch(K key){
        T value=_catch.get(key);
        if(value.isExpired()){
            _catch.remove(key);
            return null;
        }
        return value;
    }
    protected void set_catch(K key,T value){
        value.setExpired(new Timestamp(System.currentTimeMillis()+cachTime));
        _catch.put(key,value);
    }
    protected void removeCatch(K key){
        _catch.remove(key);
    }
}
