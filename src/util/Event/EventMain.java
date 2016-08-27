package util.Event;

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
    public static Thread getThread(){
        return eventThread;
    }
    public static void addEventDeal(BaseEventDeal eventDeal){
        int hashCode = eventDeal.getEventClass().hashCode();
        Set<BaseEventDeal> set = map.get(hashCode);
        if(set == null){
            set = new LinkedHashSet<>();
            map.put(hashCode,set);
        }
        set.add(eventDeal);
    }
    public static void removeEventDeal(BaseEventDeal eventDeal) {
        removeEventDeal.add(eventDeal);
    }
    public static void triggerEvent(BaseEvent event){
        events.add(event);
    }

    public static void Init(){
        EventMain eventMain = new EventMain();
        eventThread = new Thread(eventMain);
        eventThread.start();
        addEventDeal(new EventDealOnStatusAdd());
        addEventDeal(new EventDealOnStatusChange());
    }
    private static Map<Integer,Set<BaseEventDeal>> map = new ConcurrentHashMap<>();
    private static BlockingQueue<BaseEvent> events = new LinkedBlockingQueue<>();
    private static BlockingQueue<BaseEventDeal> removeEventDeal =new LinkedBlockingQueue<>();
    private static Thread eventThread;
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
                    for(BaseEventDeal deal : set){
                        deal.run(event);
                        remvoeEventDeal();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Tool.sleep(1000);
            }
        }
    }
}
