/**
 * 
 */
package com.github.glue.mvc.view;

import java.util.Arrays;
import java.util.Locale;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


/**
 * @author eric
 *
 */
public class ThymeleafViewResolver extends ViewResolver {
	private TemplateEngine templateEngine;
	
	
	
	
	@Override
	public View resolveView(String viewName, Locale locale){
		return new ThymeleafView(templateEngine, viewName,this);	
	}




	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}



	
}
