package dao;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by QAQ on 2016/10/10.
 */
public abstract class BaseCacheLRU<K,V> {
    private Map<K,V> _catch;

    public BaseCacheLRU(final int maxSize){
        LinkedHashMap<K,V> _map = new LinkedHashMap<K,V>((int)(maxSize*0.75f)+1,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size()>maxSize;
            }
        };
        _catch = Collections.synchronizedMap(_map);
    }

    public V getBeanByKey(K key){
        V bean = getBeanFromCatch(key);
        if(bean==null){
            bean=getByKeyFromSQL(key);
            if(bean!=null)
                set_catch(key,bean);
        }
        return bean;
    }
    protected abstract V getByKeyFromSQL(K key);
    private V getBeanFromCatch(K key){
        return _catch.get(key);
    }
    protected void set_catch(K key, V value){
        _catch.put(key, value);
    }
}
