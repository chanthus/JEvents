JEvents
=======

A non-annotation driven event framework for java

Release Jar Download
--------------------

https://github.com/chanthus/JEvents/blob/master/jevents-v1.0.jar?raw=true

How to use
----------

**Step 1:** Create an interface for the event (The following is just an example. It can be any interface)
````Java
public interface IListener {
	public void listenerFired(String string, int integer);
}
````
**Step 2:** Initialize JEvent inside the class that has to act as the observable
````Java
public class ClassWithEvent {

	// Initialize an test event that has the IListener interface
	public final JEvent<IListener> event = JEvent.create(IListener.class);

	public ClassWithEvent() {
		changeText("Firing event");
	}

	public void changeText(String text) {
		// Fires the event
		event.get().listenerFired(text, 100);

		/*
		 * NOTE: get() method of the event returns a proxy that has the same
		 * interface as the the interface used to construct the event.
		 * 
		 * Calling get() method from any other class would return null. Only the
		 * owner of the event can invoke the event.
		 */
	}
}
````
**Step 3:** Registering for the event
````Java
public class TestMain {
	public static void main(String[] args) {

		// Initialize the class with event
		ClassWithEvent et = new ClassWithEvent();

		// Register a class to receive event
		et.event.addListener(new IListener() {
			@Override
			public void listenerFired(String string, int integer) {
				System.out.println(string + " : " + integer);
			}
		});

		/*
		 * NOTE: The event observer class does not have to be an anonymous 
		 * inner class as shown in this example
		 */
	}
}
````
**See JEvents.Sample for a complete working sample**
