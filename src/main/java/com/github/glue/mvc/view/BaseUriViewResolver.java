/**
 * 
 */
package com.github.glue.mvc.view;

import java.util.Locale;

/**
 * @author Ecric.Lee
 *
 */
public abstract class BaseUriViewResolver extends ViewResolver{
	protected String prefix = "";
	protected String suffix = "";
	
	public abstract View resolveView(String viewName, Locale locale);

	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}