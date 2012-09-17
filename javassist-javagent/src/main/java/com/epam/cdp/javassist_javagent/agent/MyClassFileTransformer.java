package com.epam.cdp.javassist_javagent.agent;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.cdp.javassist_javagent.javasisst.MyJavassitHelper;

public class MyClassFileTransformer implements ClassFileTransformer {

	private static final String CLASS_FOR_TRANSFORMING = "com.epam.cdp.javassist_javagent.JavassistDemo";
	private static final String CLASS_FOR_TRANSFORMING_DIR = "com/epam/cdp/javassist_javagent/JavassistDemo";
	
	static final Logger logger = LoggerFactory
			.getLogger(MyClassFileTransformer.class);


	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        logger.info("class file transformer invoked for className: {}", className);

        if (className.equals(CLASS_FOR_TRANSFORMING_DIR)){
        	try {
        		logger.info("start transform for className: {}", className);     		
        		classfileBuffer = doTransformClass();
				logger.info("end transform for className: {}", className);
			} catch (InstantiationException e) {
				logger.error("", e);
			} catch (IllegalAccessException e) {
				logger.error("", e);
			} catch (NotFoundException e) {
				logger.error("", e);
			} catch (CannotCompileException e) {
				logger.error("", e);
			}catch (IOException e) {
				logger.error("", e);
			}
        }        
        return classfileBuffer;
    }

	private byte[] doTransformClass() throws NotFoundException,
			CannotCompileException, InstantiationException,
			IllegalAccessException, IOException {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.get(CLASS_FOR_TRANSFORMING);
		CtMethod[] methods = cc.getDeclaredMethods();
		for (CtMethod method : methods) {
			MyJavassitHelper.addTiming(method, cc);
			MyJavassitHelper.catchException(pool, method);
		}
	return cc.toBytecode();
	}

}