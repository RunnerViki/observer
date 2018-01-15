package com.viki.observer.common.bean;

import com.viki.observer.common.utils.Constants;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/9 16:39
 */
public class InvokeMap {

    private static ConcurrentHashMap<String, InvokeTree> invokeMap = new ConcurrentHashMap<>(1000);

    private static ConcurrentHashMap<String, InvokeTree> rootInvokeMap = new ConcurrentHashMap<>(100);

    public static void put(InvokeTree invokeTree){
        if(invokeTree.getParentNode() != null){
            invokeMap.put(String.format(Constants.INVOKE_TREE_FORMATTER, invokeTree.getParentNode().getClassName(), invokeTree.getParentNode().getMethodName(), invokeTree.getClassName(), invokeTree.getMethodName()), invokeTree);
        }else{
            invokeMap.put(String.format(Constants.INVOKE_TREE_FORMATTER, "", "", invokeTree.getClassName(), invokeTree.getMethodName()), invokeTree);
        }
    }

    public static InvokeTree getOrCreate(String grandParentClassName, String grandParentMethod, String parentClassName, String parentMethod, String className, String method){
        String invokeTreeKey;
        if(parentClassName != null && parentMethod != null){
            invokeTreeKey = String.format(Constants.INVOKE_TREE_FORMATTER, parentClassName, parentMethod, className, method);
        }else{
            invokeTreeKey = String.format(Constants.INVOKE_TREE_FORMATTER, "", "", className, method);
        }
        InvokeTree invokeTree = invokeMap.get(invokeTreeKey);
        if(invokeTree == null){
            InvokeTree sourceInvokeTree = null;
            if(parentClassName != null && !parentClassName.isEmpty() && parentMethod != null && !parentMethod.isEmpty()){
                if(grandParentClassName != null && grandParentMethod != null){
                    invokeTreeKey = String.format(Constants.INVOKE_TREE_FORMATTER, grandParentClassName, grandParentMethod, parentClassName, parentMethod);
                }else{
                    invokeTreeKey = String.format(Constants.INVOKE_TREE_FORMATTER, "", "", parentClassName, parentMethod);
                }
                sourceInvokeTree = invokeMap.getOrDefault(invokeTreeKey, new InvokeTree(null, parentClassName, parentMethod));
                put(sourceInvokeTree);
                if(sourceInvokeTree.getParentNode() == null){
                    rootInvokeMap.put(String.format(Constants.INVOKE_TREE_FORMATTER, "", "", parentClassName, parentMethod), sourceInvokeTree);
                }
            }
            invokeTree = new InvokeTree(sourceInvokeTree, className, method);
        }
        put(invokeTree);
        if(invokeTree.getParentNode() == null){
            rootInvokeMap.put(String.format(Constants.INVOKE_TREE_FORMATTER, "", "", className, method), invokeTree);
        }
        return invokeTree;
    }

    public static Enumeration<String>  getRootKeys(){
        return invokeMap.keys();
    }

    public static InvokeTree getByKey(String key){
        return invokeMap.get(key);
    }

}
