package util.Event;

import entity.Title.AllTitle.Title_MengNew;
import util.Event.EvnetDeals.EventDealOnStatusAdd;
import util.Event.EvnetDeals.EventDealOnStatusChange;
import util.Tool;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by QAQ on 2016/8/27.
 */
public class EventMain implements Runnable{
    private static Map<Integer,Set<BaseEventDeal>> map = new ConcurrentHashMap<>();
    private static BlockingQueue<BaseEvent> events = new LinkedBlockingQueue<>();
    private static BlockingQueue<BaseEventDeal> removeEventDeal =new LinkedBlockingQueue<>();
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

    private static void remvoeEventDeal(){
        BaseEventDeal eventDeal;
        while((eventDeal = removeEventDeal.poll())!=null) {
            int hashCode = eventDeal.getEventClass().hashCode();
            Set<BaseEventDeal> set = map.get(hashCode);
            if(set!=null)set.remove(eventDeal);
        }
    }
    @Override
    public void run() {
        while(true){
            try {
                BaseEvent event=events.take();
                Set<BaseEventDeal> set = map.get(event.getClass().hashCode());
                if(set != null){
                    remvoeEventDeal();
                    event.before();
                    for(BaseEventDeal deal : set){
                        deal.run(event);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Tool.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
