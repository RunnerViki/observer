package com.viki.observer;

import com.viki.observer.common.config.SystemConfig;
import com.viki.observer.transformer.TimeCostTransformer;

import java.lang.instrument.Instrumentation;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/8 21:46
 */
public class Premain {

    public static void premain(String options, Instrumentation ins) {
        SystemConfig systemConfig = SystemConfig.getInstance();
        if(systemConfig.getTargetPackage() != null){
            ins.addTransformer(new TimeCostTransformer());
        }
    }
}