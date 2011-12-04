/**
 * 
 */
package com.github.glue.mvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


/**
 * @author eric
 *
 */
public abstract class Reply {
	
	String contentType;
	List<Cookie> cookies;
	Map<String, String> headers;
	Object content;
	String templateName;
	int status = HttpServletResponse.SC_OK;
	
	public Reply type(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public Reply headers(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}
	
	public Reply header(String name, String value){
		if(headers == null){
			headers = Maps.newHashMap();
		}
		headers.put(name, value);
		return this;
	}
	
	public Reply cookies(List<Cookie> cookies) {
		this.cookies = cookies;
		return this;
	}
	
	public Reply cookie(Cookie cookie){
		if(cookies == null){
			cookies = Lists.newArrayList();
		}
		cookies.add(cookie);
		return this;
	}

	Reply with(Object content) {
		this.content = content;
		return this;
	}

	public Reply status(int code) {
		this.status = code;
		return this;
	}


	
	abstract void populate(Container container, HttpServletRequest request, HttpServletResponse response) throws IOException;

	public static DefaultReply as(){
		return new DefaultReply();
	}
	
	public static TemplateReply asTemplate(){
		return new TemplateReply();
	}
	public static TemplateReply asTemplate(String typeName){
		return new TemplateReply(typeName);
	}
	public static JsonReply asJson(){
		return new JsonReply();
	}
//	public static Reply asXml(){
//		return null;
//	}
}
