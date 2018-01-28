package com.viki.observer.strategy;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/9 18:05
 */
public class TimeSpend {
    public TimeSpend(long startTime, long endTime, long threadId){
        this.startTime = startTime;
        this.endTime = endTime;
        this.threadId = threadId;
    }

    private long startTime;

    private long endTime;

    private long threadId;

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "" + startTime +
                "," + endTime +
                ","+(endTime - startTime)+
                ","+threadId+
                "";
    }
}
