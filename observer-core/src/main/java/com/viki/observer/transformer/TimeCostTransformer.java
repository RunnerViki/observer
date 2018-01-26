package com.viki.observer.transformer;

import com.viki.observer.common.config.SystemConfig;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/9 15:52
 */
public class TimeCostTransformer implements ClassFileTransformer {

    private String transformerName = "timecost";

    private String targetPackage;

    private long timeCostThreshold;


    public TimeCostTransformer(){
        this.targetPackage = SystemConfig.getInstance().getTargetPackage();
        this.timeCostThreshold = SystemConfig.getInstance().getTimeCostThreshold();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        className = className.replace("/", ".");
        if(null == className){
            return null;
        }
        CtClass cc;
        try {
            cc = ClassPool.getDefault().get(className);
        } catch (NotFoundException e) {
            return null;
        }
        try{
            if(!className.startsWith(targetPackage) || className.contains("$")){
                return cc.toBytecode();
            }
            if((cc.getModifiers() & AccessFlag.ABSTRACT) == AccessFlag.ABSTRACT){
                return cc.toBytecode();
            }
            Arrays.stream(cc.getDeclaredMethods())
                    .filter(method -> (method.getModifiers() & AccessFlag.STATIC) != AccessFlag.STATIC && (method.getModifiers() & AccessFlag.ABSTRACT) != AccessFlag.ABSTRACT)
                    .forEach(
                            method -> {
                                try {
//                                    System.out.println(method.getLongName());
                                    method.addLocalVariable("startTime", CtClass.longType);
                                    method.addLocalVariable("endTime", CtClass.longType);
                                    method.addLocalVariable("currentClassName", ClassPool.getDefault().get(String.class.getName()));
                                    method.addLocalVariable("currentMethodName", ClassPool.getDefault().get(String.class.getName()));
                                    method.addLocalVariable("parentClassName", ClassPool.getDefault().get(String.class.getName()));
                                    method.addLocalVariable("parentMethodName", ClassPool.getDefault().get(String.class.getName()));
                                    method.addLocalVariable("grandParentClassName", ClassPool.getDefault().get(String.class.getName()));
                                    method.addLocalVariable("grandParentMethodName", ClassPool.getDefault().get(String.class.getName()));
                                    method.addLocalVariable("timeSpendMonitor", ClassPool.getDefault().get("com.viki.observer.monitor.TimeSpendMonitor"));
                                    method.addLocalVariable("timeSpendStatisticsStrategy", ClassPool.getDefault().get("com.viki.observer.strategy.TimeSpendStatisticsStrategy"));
                                    method.addLocalVariable("invokeTree", ClassPool.getDefault().get("com.viki.observer.common.bean.InvokeTree"));
                                    method.addLocalVariable("stackTrace", ClassPool.getDefault().get(StackTraceElement.class.getName()+"[]"));
                                    method.insertBefore("startTime = System.nanoTime();\n" +
                                            "                 currentClassName = \"\";\n" +
                                            "                 timeSpendMonitor = com.viki.observer.monitor.TimeSpendMonitor.getInstance();\n" +
                                            "                 timeSpendStatisticsStrategy = new com.viki.observer.strategy.TimeSpendStatisticsStrategy(timeSpendMonitor);\n" +
                                            "                 invokeTree = null;\n" +
                                            "                 currentMethodName = \"\";\n" +
                                            "                 parentClassName = \"\";\n" +
                                            "                 parentMethodName = \"\";\n" +
                                            "                 grandParentClassName = \"\";\n" +
                                            "                 grandParentMethodName = \"\";\n" +
                                            "                 stackTrace = Thread.currentThread().getStackTrace();\n" +
                                            "                 if(stackTrace.length > 1){\n" +
                                            "                   currentClassName = stackTrace[1].getClassName();\n" +
                                            "                   currentMethodName = stackTrace[1].getMethodName();\n" +
                                            "                 }\n" +
                                            "               if(stackTrace.length > 2){\n" +
                                            "                   parentClassName = stackTrace[2].getClassName();\n" +
                                            "                   parentMethodName = stackTrace[2].getMethodName();\n" +
                                            "               }" +
                                            "               if(stackTrace.length > 3){\n" +
                                            "                   grandParentClassName = stackTrace[3].getClassName();\n" +
                                            "                   grandParentMethodName = stackTrace[3].getMethodName();\n" +
                                            "               }" +
                                            "");
                                    method.insertAfter("endTime = System.nanoTime();\n" +
                                            "               invokeTree = com.viki.observer.common.bean.InvokeMap.getOrCreate(grandParentClassName, grandParentMethodName, parentClassName, parentMethodName, currentClassName, currentMethodName);"+
                                            "               timeSpendStatisticsStrategy = (com.viki.observer.strategy.TimeSpendStatisticsStrategy)invokeTree.addObserverStrategy(\""+transformerName+"\", timeSpendStatisticsStrategy);" +
                                            "               timeSpendStatisticsStrategy.add(startTime, endTime);"
                                    );
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                    );
            return cc.toBytecode();
        }catch (Exception e){
            e.printStackTrace();
            if(cc != null){
                try {
                    return cc.toBytecode();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (CannotCompileException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
}
