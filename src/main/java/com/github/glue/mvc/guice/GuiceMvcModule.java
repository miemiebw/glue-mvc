/**
 * 
 */
package com.github.glue.mvc.guice;

import com.github.glue.mvc.RequestDefinitionScanner;
import com.google.inject.AbstractModule;

/**
 * @author Ecric.Lee
 *
 */
public abstract class GuiceMvcModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(RequestDefinitionScanner.class).toInstance(new RequestDefinitionScanner(getActionPackages()));
	}
	
	public abstract String[] getActionPackages();

}
