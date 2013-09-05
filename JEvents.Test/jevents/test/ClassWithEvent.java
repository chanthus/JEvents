package jevents.test;

import com.cs.jevents.IDefaultListener;
import com.cs.jevents.JEvent;

public class ClassWithEvent {

	public final JEvent<IDefaultListener> defaultEvent = JEvent.create();
	public final JEvent<IListener> event = JEvent.create(IListener.class);

	public ClassWithEvent() {
		firePrivate();
	}

	public void firePublic() {
		firePrivate();
	}

	private void firePrivate() {
		event.get().listenerFired("Event 1", 44);
		defaultEvent.get().eventFired(this, "Default Event Args");
	}

	public boolean clear() {
		return event.clear();
	}
}
