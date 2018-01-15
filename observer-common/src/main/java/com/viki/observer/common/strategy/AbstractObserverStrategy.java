package com.viki.observer.common.strategy;


import com.viki.observer.common.monitor.Monitor;

import java.util.ArrayList;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/8 21:59
 */
public abstract class AbstractObserverStrategy<T> {

    private Monitor monitor;

    public AbstractObserverStrategy(Monitor monitor){
        this.monitor = monitor;
    }



    protected ArrayList<T> items = new ArrayList<T>();

    public ArrayList<T> getItems(){
        return items;
    }

    public void clear(){
        items.clear();
    }

    public Monitor getMonitor() {
        return monitor;
    }
}