package util.Event;

import util.Event.EvnetDeals.EventDealOnStatusAdd;
import util.Event.EvnetDeals.EventDealOnStatusChange;
import util.Tool;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 处理事件的线程。
 * 单例处理。
 * Created by QAQ on 2016/8/27.
 */
public class EventMain implements Runnable{
    /**
     * 每种类型的事件处理器存放在不同的Set中。key是事件类型的hash值
     */
    private static Map<Integer,Set<BaseEventDeal>> map = new ConcurrentHashMap<>();
    /**
     * 事件队列。触发的事件依次进入该队列中等待处理。
     * 生产者 —— 消费者 模式
     */
    private static BlockingQueue<BaseEvent> events = new LinkedBlockingQueue<>();
    private static BlockingQueue<BaseEventDeal> removeEventDeal =new LinkedBlockingQueue<>();
    /**
     * 处理事件的线程
     */
    private static Thread eventThread;

    public static Thread getThread(){
        return eventThread;
    }

    public static void Init(){
        EventMain eventMain = new EventMain();
        eventThread = new Thread(eventMain);
        eventThread.start();
        addEventDeal(new EventDealOnStatusAdd());
        addEventDeal(new EventDealOnStatusChange());

        //addEventDeal(new Title_MengNew());
    }

    /**
     * 添加事件处理器，重复添加会处理多次
     * 要移除调用removeEventDeal
     * @param eventDeal 事件处理器
     */
    public static void addEventDeal(BaseEventDeal eventDeal){
        int hashCode = eventDeal.getEventClass().hashCode();
        Set<BaseEventDeal> set = map.get(hashCode);
        if(set == null){
            set = new LinkedHashSet<>();
            map.put(hashCode,set);
        }
        set.add(eventDeal);
    }

    /**
     * 移除事件处理器，移除后不再处理事件
     * @param eventDeal 事件处理器
     */
    public static void removeEventDeal(BaseEventDeal eventDeal) {
        eventDeal.isRemoved = true;
        removeEventDeal.add(eventDeal);
    }

    public static void triggerEvent(BaseEvent event){
        events.add(event);
    }

    private static void removeEventDeal(){
        BaseEventDeal eventDeal;
        while((eventDeal = removeEventDeal.poll())!=null) {
            int hashCode = eventDeal.getEventClass().hashCode();
            Set<BaseEventDeal> set = map.get(hashCode);
            if(set!=null)set.remove(eventDeal);
        }
    }

    /**
     * 事件处理运行过程
     * 从队列中取出事件，然后依次交给map中所有对应的事件处理器依次调用事件处理的run方法
     */
    @Override
    public void run() {
        while(true){
            try {
                BaseEvent event=events.take();
                Set<BaseEventDeal> set = map.get(event.getClass().hashCode());
                if(set != null){
                    removeEventDeal();
                    event.before();
                    for(BaseEventDeal deal : set){
                        deal.run(event);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Tool.sleep(1000);
            } catch (Exception e){ //接受所有异常以免线程因为异常而停止运行
                e.printStackTrace();
            }
        }
    }
}
