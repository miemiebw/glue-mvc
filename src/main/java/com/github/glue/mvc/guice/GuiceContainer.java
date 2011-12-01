/*
 * Copyright Eric Lee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.glue.mvc.guice;

import java.util.Set;

import com.github.glue.mvc.IocContainer;
import com.google.inject.Injector;

/**
 * @author Eric.Lee
 *
 */
public class GuiceContainer implements IocContainer {
	private Injector injector;
	
	public GuiceContainer(Injector injector) {
		super();
		this.injector = injector;
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstance(java.lang.Class)
	 */
	@Override
	public <T> T getInstance(Class<T> type) {
		return injector.getInstance(type);
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstance(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> T getInstance(Class<T> type, String name) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#inject(java.lang.Object)
	 */
	@Override
	public void inject(Object o) {
		injector.injectMembers(o);
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstanceNames(java.lang.Class)
	 */
	@Override
	public Set<String> getInstanceNames(Class<?> type) {
		return null;
	}

	public Injector getInjector() {
		return injector;
	}
	
	

}
