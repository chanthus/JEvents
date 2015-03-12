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
package com.cs.jevents.security;

import sun.reflect.Reflection;

public class ReflectionProvider implements ISecurityProvider {
	@Override
	public String getCallerClass(int level) {
		return Reflection.getCallerClass(level).getName();
	}
}