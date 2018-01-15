package com.viki.observer.common.monitor;

import java.util.concurrent.TimeUnit;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/11 15:05
 */
public interface Monitor extends Runnable{

    public long getInitialDelay();

    public long getPeriod();

    public TimeUnit getTimeUnit();

}
