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
	private TemplateMode model = TemplateMode.HTML5;
	private Long cacheTTLMs = 0L;
	private String characterEncoding = "UTF-8";
	
	
	@Override
	public View resolveView(String viewName, Locale locale){
		if(templateEngine == null){
			ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
			templateResolver.setTemplateMode(model);
			templateResolver.setPrefix(prefix);
			templateResolver.setSuffix(suffix);
			templateResolver.setCacheTTLMs(cacheTTLMs);
			templateResolver.setCharacterEncoding(characterEncoding);
			
			templateEngine = new TemplateEngine();
			templateEngine.setTemplateResolver(templateResolver);
		}

		return new ThymeleafView(templateEngine, viewName,this);
			
	}


	public TemplateMode getModel() {
		return model;
	}


	public void setModel(TemplateMode model) {
		this.model = model;
	}


	public Long getCacheTTLMs() {
		return cacheTTLMs;
	}


	public void setCacheTTLMs(Long cacheTTLMs) {
		this.cacheTTLMs = cacheTTLMs;
	}


	public String getCharacterEncoding() {
		return characterEncoding;
	}


	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ThymeleafViewResolver [prefix=");
		builder.append(prefix);
		builder.append(", suffix=");
		builder.append(suffix);
		builder.append(", viewNames=");
		builder.append(viewName);
		builder.append(", contentType=");
		builder.append(contentType);
		builder.append(", model=");
		builder.append(model);
		builder.append(", cacheTTLMs=");
		builder.append(cacheTTLMs);
		builder.append(", characterEncoding=");
		builder.append(characterEncoding);
		builder.append("]");
		return builder.toString();
	}





	
}
