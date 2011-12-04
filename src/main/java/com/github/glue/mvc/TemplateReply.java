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
	void populate(Container container, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		ViewConfig viewConfig = container.getInstance(ViewConfig.class);
		
		ViewResolver viewResolver = null;
		
		if(!Strings.isNullOrEmpty(typeName)){
			viewResolver = viewConfig.getViewResolver(typeName);
		}else{
			viewResolver = viewConfig.getViewResolver();
		}
		
		if(viewResolver == null){
			throw new RuntimeException("Can't find '"+ typeName +"' ViewResolver.");
		}
		View view = viewResolver.resolveView(templateName, request.getLocale());
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
