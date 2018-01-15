package com.viki.observer.common.bean;


import com.viki.observer.common.strategy.AbstractObserverStrategy;
import com.viki.observer.common.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/8 21:48
 */
public class InvokeTree<T extends AbstractObserverStrategy> {

    private InvokeTree parentNode;

    /** sub nodes with order */
    private ArrayList<InvokeTree> subNodes;

    /** class name with package name*/
    private String className;

    /** method Name with param list, distinguish overload methods*/
    private String methodName;


    public Map<String, AbstractObserverStrategy> abstractObserverStrategySet = new HashMap(4,1);

    public InvokeTree(InvokeTree parentNode, String className, String methodName){
        this(parentNode, className, methodName, 10);
    }

    public InvokeTree(InvokeTree parentNode, String className, String methodName, int subNodeCnt){
        if(null == className || "".equals(className)){
            throw new NullPointerException("className "+className+" could not be null");
        }
        if(null == methodName || "".equals(methodName)){
            throw new NullPointerException("methodName "+methodName+" could not be null");
        }
        this.parentNode = parentNode;
        this.className = className;
        this.methodName = methodName;
        this.subNodes = new ArrayList<InvokeTree>(subNodeCnt);
    }

    public <T extends AbstractObserverStrategy> T addObserverStrategy(String name, T abstractObserverStrategy){
        AbstractObserverStrategy abstractObserverStrategy1;
        if((abstractObserverStrategy1 = abstractObserverStrategySet.get(name)) != null){
            return (T)abstractObserverStrategy1;
        }else{
            abstractObserverStrategySet.put(name, abstractObserverStrategy);
            return abstractObserverStrategy;
        }
    }

    public InvokeTree getParentNode() {
        return parentNode;
    }

    public ArrayList<InvokeTree> getSubNodes() {
        return subNodes;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }



    public Map<String, AbstractObserverStrategy> getAbstractObserverStrategySet() {
        return abstractObserverStrategySet;
    }

    @Override
    public String toString() {
        return String.format(Constants.INVOKE_TREE_TOSTRING_FORMATTER, this.className, this.methodName);
    }
}
