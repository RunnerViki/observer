package com.viki.observer.monitor;

import com.viki.observer.common.bean.InvokeMap;
import com.viki.observer.common.bean.InvokeTree;
import com.viki.observer.common.monitor.Monitor;
import com.viki.observer.common.monitor.MonitorCollector;
import com.viki.observer.common.strategy.AbstractObserverStrategy;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/11 15:05
 */
public class TimeSpendMonitor implements Monitor {

    /*static {
        TimeSpendMonitor timeSpendMonitor = new TimeSpendMonitor();
        MonitorCollector.getInstance().addMonitor(timeSpendMonitor);
    }*/

    private static volatile TimeSpendMonitor timeSpendMonitor;

    public static TimeSpendMonitor getInstance(){
        if(timeSpendMonitor == null){
            synchronized (TimeSpendMonitor.class){
                if(timeSpendMonitor == null){
                    timeSpendMonitor = new TimeSpendMonitor();
                }
            }
        }
        return timeSpendMonitor;
    }

    private TimeSpendMonitor(){
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = MonitorCollector.getInstance().getScheduledThreadPoolExecutor();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this, getInitialDelay(), getPeriod(), getTimeUnit());
    }

    @Override
    public void run() {
        try{
            Thread.currentThread().setName("strategy-reporter");
            System.out.println("strategy-reporter start:");
            Enumeration<String> rootKeys = InvokeMap.getRootKeys();
            String key;
            File logFile = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + File.separator + "logFile.log");
            if(!logFile.getParentFile().exists()){
                logFile.getParentFile().mkdirs();
            }
            if(!logFile.exists()){
//                logFile.createNewFile();
                Files.createFile(logFile.toPath());
            }
            System.out.println(logFile.getPath());
            BufferedWriter bufferedWriter = Files.newBufferedWriter(logFile.toPath(), Charset.forName("utf-8"), StandardOpenOption.APPEND);
            StringBuilder stringBuilder = new StringBuilder();
            while (rootKeys.hasMoreElements()){
                key = rootKeys.nextElement();
                InvokeTree invokeTree = InvokeMap.getByKey(key);
                scanInvokeTree(invokeTree, stringBuilder);
            }
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
        }catch (Exception e){e.printStackTrace();}
    }

    private static void scanInvokeTree(InvokeTree invokeTree, StringBuilder stringBuilder){
        try{
            if(invokeTree.getAbstractObserverStrategySet() != null){
                Collection<AbstractObserverStrategy> values = invokeTree.getAbstractObserverStrategySet().values();
                values.parallelStream().forEach(observerStrategy -> {
                    observerStrategy.getItems().parallelStream().forEach(item -> {
                        stringBuilder.append((invokeTree.getParentNode() != null ? invokeTree.getParentNode().toString() : "") + "," + invokeTree.toString() + "," + item.toString() + "\n");
                    });
                    observerStrategy.clear();
                });
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public long getInitialDelay() {
        return 10;
    }

    @Override
    public long getPeriod() {
        return 36;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return TimeUnit.SECONDS;
    }

}
