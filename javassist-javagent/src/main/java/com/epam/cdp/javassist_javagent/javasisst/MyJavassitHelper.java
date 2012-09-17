package com.epam.cdp.javassist_javagent.javasisst;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class MyJavassitHelper {
	private static final String EXCEPTION_CLASS = "java.lang.Exception";

	public static void addTiming(CtMethod ctMethod, CtClass clas)
			throws NotFoundException, CannotCompileException {

				
		String oldMethodName = ctMethod.getName();
		String fullOldMethodName = ctMethod.getLongName();
		String newMethodName = oldMethodName + "$impl";
		ctMethod.setName(newMethodName);

		CtMethod newMethod = CtNewMethod.copy(ctMethod, oldMethodName, clas,
				null);

		String returnType = ctMethod.getReturnType().getName();
		StringBuffer body = new StringBuffer();
		body.append("{\n");

		body.append("StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace(); \n");
		body.append("System.out.println(\"[Executing method "
				+ fullOldMethodName
				+ "],\\n\\t[called from: \"+stackTraceElements[2]+\"]\\n\"); \n");

		body.append("long startM = System.currentTimeMillis(); \n");

		if (!"void".equals(returnType)) {
			body.append(returnType + " result = ");
		}
		body.append(newMethodName + "($$);\n");

		body.append("long stopM = System.currentTimeMillis(); \n");
		body.append("long executionTime = stopM - startM; \n");
		body.append("System.out.println(\"[Method " + fullOldMethodName
				+ "]\\n\\t[Execution time:\"+executionTime+\"ms;]\\n\"); \n");

		if (!"void".equals(returnType)) {
			body.append("return result;\n");
		}
		body.append("}\n");

		newMethod.setBody(body.toString());
		clas.addMethod(newMethod);
	}

	public static void catchException(ClassPool pool, CtMethod method) throws CannotCompileException, NotFoundException {
		CtClass exception = pool.get(EXCEPTION_CLASS);
		method.addCatch("{System.out.println($e); return;}", exception);
	}
}
