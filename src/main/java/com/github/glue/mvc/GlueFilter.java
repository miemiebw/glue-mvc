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
package com.github.glue.mvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Eric.Lee
 *
 */
public class GlueFilter implements Filter{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private Container container;
	private List<RequestDefinition> requestDefinitions;
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		container = (Container) filterConfig.getServletContext().getAttribute(Container.class.getName());
		RequestDefinitionScanner scanner = container.getInstance(RequestDefinitionScanner.class);
		requestDefinitions = scanner.scan();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		try{
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse)resp;
			//select
			RequestDefinition selectedDeinition = null;
			int pathLength = request.getContextPath().length();
	        String path = request.getRequestURI().substring(pathLength).replaceAll("[/]{2,}", "/");
	        
			for (RequestDefinition definition : requestDefinitions) {
				if(definition.matches(path, request.getMethod())){
					selectedDeinition = definition;
					break;
				}
			}
			
			if(selectedDeinition == null){
				request.getRequestDispatcher(path).forward(request, response);
				return;
			}
			//
			log.debug("Request selected: {}",selectedDeinition);
			RequestHandler handler = new RequestHandler(request, response, selectedDeinition, container);
			handler.handle();
			
		}catch(Exception e){
			throw new ServletException(e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
