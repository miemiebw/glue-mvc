/**
 * 
 */
package com.github.glue.mvc.view;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * @author Ecric.Lee
 *
 */
public class JstlView implements View {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String name;
	private JstlViewResolver resolver;
	public JstlView(String name, JstlViewResolver resolver) {
		super();
		this.name = name;
		this.resolver = resolver;
	}


	/* (non-Javadoc)
	 * @see com.github.glue.mvc.view.View#render(com.github.glue.mvc.ModelAndView, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String url = resolver.getPrefix() + "/" + name + resolver.getSuffix();
		
		log.debug("url: {}",url);
		if(model != null){
			for (Entry<String, ?> entry : model.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
		}
		if(!Strings.isNullOrEmpty(resolver.getContentType())){
			response.setContentType(resolver.getContentType());
		}
		
		HttpServletRequest req = new HttpServletRequestWrapper((HttpServletRequest) request){
			@Override
			public String getPathInfo() {
				int servletPathLength = getServletPath().length();
		        String pathInfo = getRequestURI().substring(getContextPath().length()).replaceAll("[/]{2,}", "/");
		        pathInfo = pathInfo.length() > servletPathLength ? pathInfo.substring(servletPathLength) : null;
				return pathInfo;
			}
			
		};
		try {
			req.getRequestDispatcher(url).forward(req, response);
		} catch (ServletException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

}
