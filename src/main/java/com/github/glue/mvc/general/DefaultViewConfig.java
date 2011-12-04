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
package com.github.glue.mvc.general;


import com.github.glue.mvc.ViewConfig;
import com.github.glue.mvc.view.ViewResolver;
import com.google.common.base.Equivalences;

/**
 * @author Eric.Lee
 *
 */
public class DefaultViewConfig implements ViewConfig{
	
	private DefaultContainer container;
	
	
	
	public DefaultViewConfig(DefaultContainer container) {
		super();
		this.container = container;
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.ViewConfig#getView()
	 */
	@Override
	public ViewResolver getViewResolver() {
		for (ViewResolver resolver : container.getInstances(ViewResolver.class)) {
			return resolver;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.ViewConfig#getView(java.lang.String)
	 */
	@Override
	public ViewResolver getViewResolver(String viewName) {
		for (ViewResolver resolver : container.getInstances(ViewResolver.class)) {
			if(Equivalences.equals().equivalent(viewName, resolver.getViewName())){
				return resolver;
			}
		}
		return null;
	}

}
