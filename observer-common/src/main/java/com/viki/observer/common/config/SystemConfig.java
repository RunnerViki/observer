package com.viki.observer.common.config;

import java.util.Properties;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/9 15:32
 */
public class SystemConfig {

    private String targetPackage;

    private Long timeCostThreshold = 20L;

    private boolean invokeTreeObserverEnable = false;

    public static volatile SystemConfig systemConfig;

    private SystemConfig(Properties properties){
            targetPackage = properties.getProperty("observer.package");
            if(targetPackage == null || targetPackage.isEmpty()){
                System.err.println("observer.package should be assigned. Please check your config.");
                System.exit(-1);
            }
            String timecost = properties.getProperty("observer.timecost");
            try{
                timeCostThreshold = Long.parseLong(timecost);
            }catch (NumberFormatException e){
                System.err.println("observer.timecost is not an available number, default "+timeCostThreshold + " instead.");
            }
            invokeTreeObserverEnable = Boolean.getBoolean(properties.getProperty("observer.method.invoketree.enable", "false"));
    }

    public static SystemConfig getInstance(){
        if(systemConfig == null){
            synchronized (SystemConfig.class){
                if(systemConfig == null){
                    systemConfig = new SystemConfig(System.getProperties());
                }
            }
        }
        return systemConfig;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public Long getTimeCostThreshold() {
        return timeCostThreshold;
    }

    public void setTimeCostThreshold(Long timeCostThreshold) {
        this.timeCostThreshold = timeCostThreshold;
    }

    public boolean isInvokeTreeObserverEnable() {
        return invokeTreeObserverEnable;
    }

    public void setInvokeTreeObserverEnable(boolean invokeTreeObserverEnable) {
        this.invokeTreeObserverEnable = invokeTreeObserverEnable;
    }
}
