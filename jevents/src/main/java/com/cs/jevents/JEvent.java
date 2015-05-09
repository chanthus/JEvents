package com.cs.jevents;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.reflect.Proxy.newProxyInstance;

public class JEvent<T> {

    private T listener;
    private String permissionClassName;

    private final Object lock = new Object();
    private List<T> listenersList = new LinkedList<T>();
    private static StacktraceProvider securityProvider = new StacktraceProvider();

    private JEvent() {
    }

    public JEvent(Class<T> classType) {
        this(classType, securityProvider.getCallerClass(3));
    }

    private JEvent(Class<T> classType, String permissionClassName) {
        this.permissionClassName = permissionClassName;
        ListenerProxy<T> listenerProxy = new ListenerProxy<T>(listenersList, lock);
        this.listener = (T) newProxyInstance(getClass().getClassLoader(), new Class[]{classType}, listenerProxy);
    }

    public static <I> JEvent<I> create(Class<I> classType) {
        return new JEvent<I>(classType, securityProvider.getCallerClass(3));
    }

    public static JEvent<DefaultListener> create() {
        return new JEvent<DefaultListener>(DefaultListener.class, securityProvider.getCallerClass(3));
    }

    public T get() {
        synchronized (lock) {
            return hasPermission() ? listener : null;
        }
    }

    public boolean addListener(T listener) {
        synchronized (lock) {
            return !listenersList.contains(listener) && listenersList.add(listener);
        }
    }

    public boolean removeListener(T listener) {
        synchronized (lock) {
            return listenersList.contains(listener) && listenersList.remove(listener);
        }
    }

    public void clear() {
        synchronized (lock) {
            if (hasPermission()) {
                listenersList.clear();
            }
        }
    }

    private boolean hasPermission() {
        return securityProvider.getCallerClass(4).equals(permissionClassName);
    }
}