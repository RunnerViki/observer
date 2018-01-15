package com.viki.observer.transformer;

import com.viki.observer.common.config.SystemConfig;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * Function: TODO
 *
 * @author Viki
 * @date 2018/1/9 16:30
 */
public class InvokeTreeTransformer implements ClassFileTransformer {


    private String targetPackage;

    protected InvokeTreeTransformer(SystemConfig systemConfig){
        this.targetPackage = systemConfig.getTargetPackage().replace(".", "/");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if(null == className || !className.startsWith(targetPackage)){
            return null;
        }

        try {
            CtClass cc = ClassPool.getDefault().get(className);

        } catch (NotFoundException e) {
            e.printStackTrace();
        }


        return new byte[0];
    }
}
