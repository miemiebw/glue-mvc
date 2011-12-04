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

import javax.inject.Singleton;

import com.github.glue.mvc.ViewConfig;
import com.github.glue.mvc.view.View;
import com.github.glue.mvc.view.ViewResolver;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * @author Eric.Lee
 *
 */
@Singleton
public class GuiceViewConfig implements ViewConfig{
	
	private Injector injector;
	
	@Inject
	public GuiceViewConfig(Injector injector) {
		super();
		this.injector = injector;
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.ViewConfig#getView()
	 */
	@Override
	public ViewResolver getViewResolver() {
		return injector.getInstance(ViewResolver.class);
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.ViewConfig#getView(java.lang.String)
	 */
	@Override
	public ViewResolver getViewResolver(String viewName) {
		return injector.getInstance(Key.get(ViewResolver.class, Names.named(viewName)));
	}

}
