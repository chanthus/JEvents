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
				System.out
						.println("PASS: Inner Class: Listener fired with String: "
								+ string + " and Integer: " + integer);
			}
		});

		et.defaultEvent.addListener(new IDefaultListener() {
			@Override
			public void eventFired(Object sender, Object args) {
				count++;
				System.out.println("PASS: Default Listener Fired with args: "
						+ args);
			}
		});

		double start = System.currentTimeMillis();

		JEvent<IListener> event = JEvent.create(IListener.class);
		event.get().listenerFired("", 0);

		System.out.println("Construction Time millis: "
				+ (System.currentTimeMillis() - start) + "\n");

		Main main = new Main();

		et.event.addListener(main);
		et.event.addListener(main);
		et.event.removeListener(main);
		et.event.addListener(main);

		start = System.currentTimeMillis();

		try {
			et.firePublic();
			System.out.println("PASS: Modified listener list while invoking");
		} catch (Exception e) {
			System.out.println("FAIL: Modified listener list while invoking");
		}

		System.out.println("\nInvoking Time millis: "
				+ (System.currentTimeMillis() - start) + "\n");

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					et.event.get().listenerFired("Chanthu", 2);
					System.out
							.println("FAIL: Invoked event from an external thread!");
				} catch (Exception ex) {
					System.out
							.println("PASS: Failed to invoke event from an external thread!");
				}
			}
		}).start();

		try {
			et.event.get().listenerFired("Chanthu1", 20);
			System.out.println("FAIL: Invoked event from an external class!");
		} catch (Exception e) {
			System.out
					.println("PASS: Failed to invoke event from an external class!");
		}

		String clearSuccess = et.event.clear() == true ? "FAIL" : "PASS";
		System.out.println(clearSuccess
				+ ": Listener clearing from external class");

		clearSuccess = et.clear() == false ? "FAIL" : "PASS";
		System.out.println(clearSuccess
				+ ": Listener clearing from external class");

		clearSuccess = count != 4 ? "FAIL" : "PASS";
		System.out.println(clearSuccess + ": Invoked count is 4");
	}

	@Override
	public void listenerFired(String string, int integer) {
		count++;
		System.out.println("PASS: Implementation: Listener fired with String: "
				+ string + " and Integer: " + integer);
	}
}
