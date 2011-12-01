/**
 * 
 */
package com.github.glue.mvc;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.glue.mvc.view.View;
import com.github.glue.mvc.view.ViewResolver;
import com.google.common.base.Equivalences;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author eric
 *
 */
public class TemplateReply extends Reply {
	
	String typeName;
	Map<String, Object> attributes = Maps.newHashMap();
	String templateName;
	
	public TemplateReply(){
		
	}
	public TemplateReply(String typeName){
		this.typeName = typeName;
	}
	
	public TemplateReply with(String name,Object value){
		attributes.put(name, value);
		return this;
	}
	
	public TemplateReply with(Map<String,Object> attributes){
		this.attributes = attributes;
		return this;
	}
	
	public TemplateReply to(String templateName){
		this.templateName = templateName;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.github.glue.mvc.Reply#populate(com.google.inject.Injector, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	void populate(IocContainer iocContainer, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		Set<ViewResolver> viewResolvers = Sets.newHashSet();
		
		Set<String> viewResolverIds = iocContainer.getInstanceNames(ViewResolver.class);
		for (String id : viewResolverIds) {
			ViewResolver resolver = iocContainer.getInstance(ViewResolver.class, id);
			if(resolver != null){
				viewResolvers.add(resolver);
			}
		}
		
		ViewResolver selectedResolver = null;
		for (ViewResolver viewResolver : viewResolvers) {
			if(Strings.isNullOrEmpty(typeName)){
				selectedResolver = viewResolver;
				break;
			}
			
			if(Equivalences.equals().equivalent(typeName, viewResolver.getViewName())){
				selectedResolver = viewResolver;
				break;
			}
		}
		
		if(selectedResolver == null){
			throw new RuntimeException("Can't find ViewResolver.");
		}
		
		View view = selectedResolver.resolveView(templateName, request.getLocale());
		view.render(attributes, request, response);
		
		if(!Strings.isNullOrEmpty(contentType)){
			response.setContentType(contentType);
		}
		if(headers != null){
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				response.setHeader(entry.getKey(), entry.getValue());
			}
		}
		if(cookies != null){
			for (Cookie cookie : cookies) {
				response.addCookie(cookie);
			}
		}
	}

}
