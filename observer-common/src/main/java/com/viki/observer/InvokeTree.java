package com.viki.observer;

import java.util.ArrayList;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/8 21:48
 */
public class InvokeTree {

    private InvokeTree parentNode;

    private ArrayList<InvokeTree> subNodes = new ArrayList<InvokeTree>();

    private String className;

    private String methodName;

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

    @Override
    public String toString() {
        return "invoke bean["+this.className+":"+this.methodName+"]";
    }
}
