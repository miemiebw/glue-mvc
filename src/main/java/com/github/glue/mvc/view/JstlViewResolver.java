/**
 * 
 */
package com.github.glue.mvc.view;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author eric
 *
 */
public class JstlViewResolver extends ViewResolver {
	

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.view.ViewResolver#resolveViewName(java.lang.String, java.util.Locale)
	 */
	@Override
	public View resolveView(String viewName, Locale locale) {
		return new JstlView(viewName,this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JstlViewResolver [viewNames=");
		builder.append(viewName);
		builder.append(", prefix=");
		builder.append(prefix);
		builder.append(", suffix=");
		builder.append(suffix);
		builder.append(", contentType=");
		builder.append(contentType);
		builder.append("]");
		return builder.toString();
	}

}
