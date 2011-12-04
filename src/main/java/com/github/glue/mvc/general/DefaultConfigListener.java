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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.Container;
import com.github.glue.mvc.RequestDefinitionScanner;
import com.github.glue.mvc.ViewConfig;
import com.github.glue.mvc.view.ViewResolver;




/**
 * @author Eric.Lee
 *
 */
public abstract class DefaultConfigListener extends FileCleanerCleanup  {
	private static final Logger log = LoggerFactory.getLogger( DefaultConfigListener.class );
	static final String CONTAINER = Container.class.getName();
	private DefaultContainer container;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		final ServletContext servletContext = servletContextEvent.getServletContext();
		container = new DefaultContainer();
		MvcConfig mvcConfig = createViewConfig();
		RequestDefinitionScanner scanner = new RequestDefinitionScanner(mvcConfig.getActionPackages());
		container.bind(RequestDefinitionScanner.class.getName(), scanner);
		
		for (ViewResolver resolver : mvcConfig.getViewResolvers()) {
			container.bind(resolver.getClass().getName()+":"+resolver.getViewName(), resolver);
		}
		container.bind(ViewConfig.class.getName(), new DefaultViewConfig(container));
		
		servletContext.setAttribute(CONTAINER, container);
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
	    servletContext.removeAttribute(CONTAINER);
	    super.contextDestroyed(servletContextEvent);
	}
	
	

	public abstract MvcConfig createViewConfig();
	

}
