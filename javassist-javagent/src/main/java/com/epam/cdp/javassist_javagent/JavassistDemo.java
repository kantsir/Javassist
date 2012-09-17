package com.epam.cdp.javassist_javagent;


public class JavassistDemo {

	public void a() {
		b();
	}

	void b() {
		c(null, new Object(), new Object(), new Integer(5));
	}

	protected void c(String z, Object... varargs) {
		d();
	}

	private void d() {
		throw new RuntimeException(
				"Time should be printed event after exception");
	}

}
