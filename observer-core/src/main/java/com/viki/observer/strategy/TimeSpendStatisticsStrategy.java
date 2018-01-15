package com.viki.observer.strategy;

import com.viki.observer.common.monitor.Monitor;
import com.viki.observer.common.strategy.AbstractObserverStrategy;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/8 22:04
 */
public class TimeSpendStatisticsStrategy extends AbstractObserverStrategy<TimeSpend> {

    public TimeSpendStatisticsStrategy(Monitor monitor) {
        super(monitor);
    }

    public void add(long startTime, long endTime){
        items.add(new TimeSpend(startTime, endTime));
    }


}
