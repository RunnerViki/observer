package com.viki.observer.common.monitor;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/11 15:05
 */
public class MonitorCollector {

    private ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    private MonitorCollector(){
        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(5);
    }

    public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor(){
        return scheduledThreadPoolExecutor;
    }

    private static volatile MonitorCollector monitorCollector;

    public static MonitorCollector getInstance(){
        if(monitorCollector == null){
            synchronized (MonitorCollector.class){
                if(monitorCollector == null){
                    monitorCollector = new MonitorCollector();
                }
            }
        }
        return monitorCollector;
    }

    public ArrayList<Monitor> getMonitorCollection() {
        return monitorCollection;
    }

    private ArrayList<Monitor> monitorCollection = new ArrayList<>();

    public void addMonitor(Monitor monitor){
        monitorCollection.add(monitor);
    }
}
