package dao;

import entity.IBeanCanCach;
import entity.User;
import util.Tool;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public abstract class BaseCache<K,T extends IBeanCanCach> {
    protected int maxSize = 100;
    protected long cachTime = 5*60*1000;//默认5分钟

    private LinkedHashMap<K,T> _catch = new LinkedHashMap<>();
    private Lock lock = new ReentrantLock();

    public T getBeanByKey(K key){
        T bean = getBeanFromCatch(key);
        if(bean==null){
            bean=getByKeyFromSQL(key);
            if(bean!=null)
                set_catch(key,bean);
        }
        return bean;
    }
    protected void removeCatch(K key){
        try {
            lock.lock();
            _catch.remove(key);
        }finally {
            lock.unlock();
        }
    }
    protected abstract T getByKeyFromSQL(K key);
    protected void clearCatch(){
        _catch.clear();
    }

    private T getBeanFromCatch(K key){
        try {
            lock.lock();
            clearExpired();
            T value = _catch.get(key);
            if (value == null) return null;
            if (value.isExpired()) {
                _catch.remove(key);
                return null;
            }
            Tool.log("GetFromCatchSuccess("+key+","+value+")");
            return value;
        }finally {
            lock.unlock();
        }
    }
    private void set_catch(K key,T value){
        value.setExpired(new Timestamp(System.currentTimeMillis()+cachTime));
        _catch.put(key, value);
    }
    private void clearExpired(){
        List<K> dels=new ArrayList<K>();
        for(Map.Entry<K,T> value:_catch.entrySet()){
            if(value.getValue().isExpired() || _catch.size()-dels.size()>maxSize){
                dels.add(value.getKey());
            }else{
                break;
            }
        }
        for(K k:dels){
            _catch.remove(k);
        }
    }
}
