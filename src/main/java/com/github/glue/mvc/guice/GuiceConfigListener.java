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

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.Container;
import com.google.inject.Injector;

/**
 * @author Eric.Lee
 *
 */
public abstract class GuiceConfigListener extends FileCleanerCleanup  {
	private static final Logger log = LoggerFactory.getLogger( GuiceConfigListener.class );
	static final String IOCCONTAINER = Container.class.getName();
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		final ServletContext servletContext = servletContextEvent.getServletContext();
	    servletContext.setAttribute(IOCCONTAINER, new GuiceContainer(createInjector()));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		GuiceContainer guiceContainer = (GuiceContainer) servletContext.getAttribute(IOCCONTAINER);
	    servletContext.removeAttribute(IOCCONTAINER);
	    destroyInjector(guiceContainer.getInjector());
	    
	    super.contextDestroyed(servletContextEvent);
	}

	

	protected abstract Injector createInjector();

	protected abstract void destroyInjector(Injector injector);
}
