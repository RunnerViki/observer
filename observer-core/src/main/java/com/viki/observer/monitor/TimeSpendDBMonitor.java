package com.viki.observer.monitor;

import com.viki.observer.common.bean.InvokeMap;
import com.viki.observer.common.bean.InvokeTree;
import com.viki.observer.common.monitor.Monitor;
import com.viki.observer.common.monitor.MonitorCollector;
import com.viki.observer.common.strategy.AbstractObserverStrategy;
import com.viki.observer.strategy.TimeSpend;
//import org.apache.ibatis.datasource.pooled.PooledDataSource;
//import org.apache.ibatis.logging.log4j.Log4jImpl;
//import org.apache.ibatis.mapping.Environment;
//import org.apache.ibatis.session.*;
//import org.apache.ibatis.transaction.Transaction;
//import org.apache.ibatis.transaction.TransactionFactory;
//import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/11 15:05
 */
public class TimeSpendDBMonitor implements Monitor {
    @Override
    public long getInitialDelay() {
        return 0;
    }

    @Override
    public long getPeriod() {
        return 0;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return null;
    }

    @Override
    public void run() {

    }/*

    *//*static {
        TimeSpendMonitor timeSpendMonitor = new TimeSpendMonitor();
        MonitorCollector.getInstance().addMonitor(timeSpendMonitor);
    }*//*

    private static volatile TimeSpendDBMonitor timeSpendMonitor;
    private SqlSessionFactory sqlSessionFactory = null;


    public static TimeSpendDBMonitor getInstance(){
        if(timeSpendMonitor == null){
            synchronized (TimeSpendDBMonitor.class){
                if(timeSpendMonitor == null){
                    timeSpendMonitor = new TimeSpendDBMonitor();
                }
            }
        }
        return timeSpendMonitor;
    }

    private TimeSpendDBMonitor(){
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = MonitorCollector.getInstance().getScheduledThreadPoolExecutor();
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this, getInitialDelay(), getPeriod(), getTimeUnit());
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        Configuration configuration = new Configuration();
        configuration.setLogImpl(org.apache.ibatis.logging.log4j.Log4jImpl.class);
        Environment environment = new Environment("observer-db", new JdbcTransactionFactory(), new PooledDataSource("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/observer", "root", "a"));
        configuration.setEnvironment(environment);
        sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration);
    }

    @Override
    public void run() {
        try{
            Thread.currentThread().setName("strategy-reporter");
            System.out.println("strategy-reporter db start:");
            Enumeration<String> rootKeys = InvokeMap.getRootKeys();
            String key;
            while (rootKeys.hasMoreElements()){
                key = rootKeys.nextElement();
                InvokeTree invokeTree = InvokeMap.getByKey(key);
                scanInvokeTree(invokeTree);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void scanInvokeTree(InvokeTree invokeTree){
        try{
            if(invokeTree.getAbstractObserverStrategySet() != null){
                Collection<AbstractObserverStrategy> values = invokeTree.getAbstractObserverStrategySet().values();
                values.stream().forEach(observerStrategy -> {
                    observerStrategy.getItems().stream().forEachOrdered(item -> {
                        SqlSession sqlSession = sqlSessionFactory.openSession(true);
                        sqlSession.insert(String.format("insert into t_observer_log(cur_method, parent_method, start_time, end_time) values('%s', '%s', %d, %d)", invokeTree.toString(),
                                        ((TimeSpend) item).getStartTime(),
                                        ((TimeSpend) item).getEndTime()));
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
*/
}
