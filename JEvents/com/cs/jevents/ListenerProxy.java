/**
 * Copyright 2013 Chanthu Shamsu
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

public final class ListenerProxy<T> implements InvocationHandler {

	private Object lock;
	private ArrayList<T> listenersList;

	public ListenerProxy(ArrayList<T> listenersList, Object lock) {
		this.lock = lock;
		this.listenersList = listenersList;
	}

	@Override
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {

		synchronized (lock) {
			for (int i = listenersList.size() - 1; i >= 0; i--) {
				T listener = listenersList.get(i);

				try {
					m.invoke(listener, args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return null;
		}
	}
}