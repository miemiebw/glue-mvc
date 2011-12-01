/**
 * 
 */
package com.github.glue.mvc.view;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

/**
 * @author eric
 *
 */
public class ThymeleafView implements View {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String name;
	private TemplateEngine templateEngine;
	private ThymeleafViewResolver resolver;
	
	public ThymeleafView(TemplateEngine templateEngine, String name,ThymeleafViewResolver resolver) {
		super();
		this.templateEngine = templateEngine;
		this.name = name;
		this.resolver = resolver;
	}


	/* (non-Javadoc)
	 * @see com.github.glue.mvc.view.View#render(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String url = resolver.getPrefix() + name + resolver.getSuffix();
		log.debug("url: {}",url);
		
		WebContext webContext = new WebContext(request, request.getLocale());
		webContext.setVariables(model);
		String result = templateEngine.process(name, webContext);
		if (response.getContentType() == null) {
			response.setContentType(resolver.getContentType());
		      
		}
        response.getWriter().write(result);
	}

}
