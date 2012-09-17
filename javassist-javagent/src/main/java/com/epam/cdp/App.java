package com.epam.cdp;

import java.util.Arrays;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.cdp.javassist_javagent.JavassistDemo;

public class App {

    static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
    	logger.info("main method invoked with args: {}", Arrays.asList(args));
        JavassistDemo demo = new JavassistDemo();
        demo.a();
    }

}