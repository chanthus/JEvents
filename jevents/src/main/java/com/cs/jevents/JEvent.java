/**
 * Github project url: https://github.com/chanthus/JEvents
 *
 * Copyright 2013 Chanthu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.cs.jevents;

import com.cs.jevents.security.ISecurityProvider;
import com.cs.jevents.security.ReflectionProvider;

import java.lang.reflect.Proxy;
import java.util.ArrayList;

public final class JEvent<T> {

    private Object lock;

    private T listener;
    private String permissionClassName;
    private ArrayList<T> listenersList;

    private static ISecurityProvider securityProvider;

    static {
        securityProvider = new ReflectionProvider();
    }

    // -------------------- START CONSTRUCTORS -------------------

    private JEvent() {
        lock = new Object();
        listenersList = new ArrayList<T>();
    }

    public JEvent(Class<T> classType) {
        this(classType, securityProvider.getCallerClass(3));
    }

    public JEvent(Class<T> classType, Class<?> permissionClass) {
        this(classType, permissionClass.getName());
    }

    public JEvent(Class<T> classType, String permissionClassName) {
        this();

        this.permissionClassName = permissionClassName;
        ListenerProxy<T> listenerProxy = new ListenerProxy<T>(listenersList, lock);
        this.listener = (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{classType}, listenerProxy);
    }

    // --------------------- END CONSTRUCTORS --------------------

    // ------------------ START STATIC CREATORS ------------------

    public static <I> JEvent<I> create(Class<I> classType) {
        return new JEvent<I>(classType, securityProvider.getCallerClass(3));
    }

    public static <I> JEvent<I> create(Class<I> classType, Class<?> permissionClass) {
        return new JEvent<I>(classType, permissionClass);
    }

    public static JEvent<IDefaultListener> create() {
        return new JEvent<IDefaultListener>(IDefaultListener.class, securityProvider.getCallerClass(3));
    }

    // ------------------- END STATIC CREATORS -------------------

    /**
     * Returns an invoker proxy that can be used to invoke the event. NOTE: Only
     * the parent of the event can get the invoker. All other classes would get
     * null when this method is called.
     *
     * @return The invoker interface
     */
    public T get() {
        synchronized (lock) {
            if (hasPermission()) {
                return listener;
            }

            return null;
        }
    }

    /**
     * Register an observer to listen to the event
     *
     * @param listener
     * @return <code>true</code> is adding is successful
     */
    public boolean addListener(T listener) {
        synchronized (lock) {
            if (!listenersList.contains(listener)) {
                return listenersList.add(listener);
            }

            return false;
        }
    }

    /**
     * Removes an observer that is already listening to the event
     *
     * @param listener
     * @return <code>true</code> is removing is successful
     */
    public boolean removeListener(T listener) {
        synchronized (lock) {
            if (listenersList.contains(listener)) {
                return listenersList.remove(listener);
            }

            return false;
        }
    }

    public boolean clear() {
        synchronized (lock) {
            if (hasPermission()) {
                listenersList.clear();
                return true;
            }
            return false;
        }
    }

    private boolean hasPermission() {
        return securityProvider.getCallerClass(4).equals(permissionClassName);
    }

    // ------------------ START GLOBAL STATICS -------------------

    public static synchronized void setSecurityProvider(ISecurityProvider securityProvider) {
        JEvent.securityProvider = securityProvider;
    }

    // ------------------- END GLOBAL STATICS --------------------
}
