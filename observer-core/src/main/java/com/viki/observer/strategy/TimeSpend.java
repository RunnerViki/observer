package com.viki.observer.strategy;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/9 18:05
 */
public class TimeSpend {
    public TimeSpend(long startTime, long endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    private long startTime;

    private long endTime;

    @Override
    public String toString() {
        return "" + startTime +
                "," + endTime +
                ","+(endTime - startTime)+
                "";
    }
}
