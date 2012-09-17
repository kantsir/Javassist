package com.epam.cdp.javassist_javagent.agent;

import java.lang.instrument.Instrumentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJavaAgent {

    static final Logger logger = LoggerFactory.getLogger(MyJavaAgent.class);

    private static Instrumentation instrumentation;

    
    public static void premain(String args, Instrumentation inst) throws Exception {
        logger.info("premain method invoked with args: {} and inst: {}", args, inst);
        instrumentation = inst;
        instrumentation.addTransformer(new MyClassFileTransformer());
    }

   
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        logger.info("agentmain method invoked with args: {} and inst: {}", args, inst);
        instrumentation = inst;
        instrumentation.addTransformer(new MyClassFileTransformer());
    }

}