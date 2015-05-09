package com.cs.jevents;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class ListenerProxy<T> implements InvocationHandler {

    private Object lock;
    private List<T> listenersList;

    public ListenerProxy(List<T> listenersList, Object lock) {
        this.lock = lock;
        this.listenersList = listenersList;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {

        synchronized (lock) {
            for (int i = listenersList.size() - 1; i >= 0; i--) {
                try {
                    m.invoke(listenersList.get(i), args);
                } catch (Exception e) {
                }
            }

            return null;
        }
    }
}