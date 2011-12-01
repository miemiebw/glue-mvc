/**
 * 
 */
package com.github.glue.mvc.view;

import java.util.Locale;

/**
 * @author Ecric.Lee
 *
 */
public abstract class ViewResolver {
	protected String prefix = "";
	protected String suffix = "";
	protected String viewName = null;
	protected String contentType = "text/html;charset=UTF-8";
	
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


	public String getViewName() {
		return viewName;
	}


	public void setViewName(String viewName) {
		this.viewName = viewName;
	}


	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}