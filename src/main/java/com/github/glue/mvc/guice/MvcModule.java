/**
 * 
 */
package com.github.glue.mvc.guice;

import com.github.glue.mvc.RequestDefinitionScanner;
import com.github.glue.mvc.ViewConfig;
import com.github.glue.mvc.view.ViewResolver;
import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 * @author Ecric.Lee
 *
 */
public abstract class MvcModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(RequestDefinitionScanner.class).toInstance(new RequestDefinitionScanner(getActionPackages()));
		bind(ViewConfig.class).to(GuiceViewConfig.class);
		ViewResolver[] resolvers = getViewResolvers();
		if(resolvers == null || resolvers.length == 0){
			return;
		}
		
		bind(ViewResolver.class).toInstance(resolvers[0]);
		if(resolvers.length != 1){
			for (ViewResolver resolver : getViewResolvers()) {
				String viewName = resolver.getViewName();
				if(Strings.isNullOrEmpty(viewName)){
					throw new RuntimeException("ViewResolver's viewName is empty.");
				}
				bind(Key.get(ViewResolver.class, Names.named(viewName))).toInstance(resolver);
			}
		}
	}
	
	public abstract String[] getActionPackages();

	public abstract ViewResolver[] getViewResolvers();
}
