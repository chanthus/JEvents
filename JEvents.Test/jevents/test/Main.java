package jevents.test;

import com.cs.jevents.IDefaultListener;
import com.cs.jevents.JEvent;
import com.cs.jevents.security.StacktraceProvider;

public class Main implements IListener {

	public static int count = 0;

	public static void main(String[] args) {

		JEvent.setSecurityProvider(new StacktraceProvider());

		final ClassWithEvent et = new ClassWithEvent();

		et.event.addListener(new IListener() {
			@Override
			public void listenerFired(String string, int integer) {
				count++;
				et.event.removeListener(this);
			}
		});

		et.event.addListener(new IListener() {
			@Override
			public void listenerFired(String string, int integer) {
				count++;
				p("PASS: Inner Class: Listener fired with String: " + string
						+ " and Integer: " + integer);
			}
		});

		et.defaultEvent.addListener(new IDefaultListener() {
			@Override
			public void eventFired(Object sender, Object args) {
				count++;
				p("PASS: Default Listener Fired with args: " + args);
			}
		});

		double start = System.currentTimeMillis();

		JEvent<IListener> event = JEvent.create(IListener.class);
		event.get().listenerFired("", 0);

		p("Construction Time millis: " + (System.currentTimeMillis() - start)
				+ "\n");

		Main main = new Main();

		et.event.addListener(main);
		et.event.addListener(main);
		et.event.removeListener(main);
		et.event.addListener(main);

		start = System.currentTimeMillis();

		try {
			et.firePublic();
			p("PASS: Modified listener list while invoking");
		} catch (Exception e) {
			p("FAIL: Modified listener list while invoking");
		}

		p("\nInvoking Time millis: " + (System.currentTimeMillis() - start)
				+ "\n");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					et.event.get().listenerFired("Chanthu", 2);
					p("FAIL: Invoked event from an external thread!");
				} catch (Exception ex) {
					p("PASS: Failed to invoke event from an external thread!");
				}
			}
		}).start();

		try {
			et.event.get().listenerFired("Chanthu1", 20);
			p("FAIL: Invoked event from an external class!");
		} catch (Exception e) {
			p("PASS: Failed to invoke event from an external class!");
		}

		String clearSuccess = et.event.clear() == true ? "FAIL" : "PASS";
		p(clearSuccess + ": Listener clearing from external class");

		clearSuccess = et.clear() == false ? "FAIL" : "PASS";
		p(clearSuccess + ": Listener clearing from external class");

		clearSuccess = count != 4 ? "FAIL" : "PASS";
		p(clearSuccess + ": Invoked count is 4");
	}

	@Override
	public void listenerFired(String string, int integer) {
		count++;
		p("PASS: Implementation: Listener fired with String: " + string
				+ " and Integer: " + integer);
	}

	private static void p(Object toPrint) {
		System.out.println(toPrint);
	}
}
