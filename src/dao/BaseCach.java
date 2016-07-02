package dao;

import entity.IBeanCanCach;
import entity.User;
import util.Tool;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by QAQ on 2016/5/26 0026.
 */
public abstract class BaseCach<K,T extends IBeanCanCach> {
    protected int maxSize = 100;
    protected long cachTime = 5*60*1000;//默认5分钟


    private ConcurrentMap<K,T> _catch = new ConcurrentHashMap<>();

    public T getBeanByKey(K key){
        T bean = getBeanFromCatch(key);
        if(bean==null){
            bean=getByKeyFromSQL(key);
            if(bean!=null)
                set_catch(key,bean);
        }
        return bean;
    }

    private T getBeanFromCatch(K key){
        if(_catch.size()>maxSize) clear();
        T value=_catch.get(key);
        if(value==null) return null;
        if(value.isExpired()){
            _catch.remove(key);
            return null;
        }
        return value;
    }
    private void set_catch(K key,T value){
        value.setExpired(new Timestamp(System.currentTimeMillis()+cachTime));
        _catch.put(key, value);
    }
    protected void removeCatch(K key){
        _catch.remove(key);
    }

    protected abstract T getByKeyFromSQL(K key);


    private Lock lock = new ReentrantLock();
    private void clear(){
        new Thread(){
            @Override
            public void run() {
                if(!lock.tryLock()) return ;
                try {
                    clearExpired();
                    while (_catch.size() > maxSize) {//删除过期的仍然超过大小
                        if(_catch.size()<10){
                            _catch.clear();
                        }else {
                            clearRandom(0.5f);
                        }
                    }
                }finally {
                    lock.unlock();
                }
            }
        }.start();
    }
    private void clearExpired(){
        List<K> dels=new ArrayList<K>();
        for(K k:_catch.keySet()){
            if(_catch.get(k).isExpired()){
                dels.add(k);
            }
        }
        for(K k:dels){
            _catch.remove(k);
        }
    }
    private void clearRandom(float rate){
        List<K> dels=new ArrayList<K>();
        for(K k:_catch.keySet()){
            if(Math.random()<=rate){
                dels.add(k);
            }
        }
        for(K k:dels){
            _catch.remove(k);
        }
    }
}
