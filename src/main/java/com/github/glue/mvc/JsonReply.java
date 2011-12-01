/**
 * 
 */
package com.github.glue.mvc;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class JsonReply extends Reply {
	
	String charset = "UTF-8";
	
	public JsonReply(){
		this.contentType = "text/json";
	}
	
	
	public JsonReply with(Object content) {
		super.with(content);
		return this;
	}
	public JsonReply charset(String charsetName){
		this.charset = charsetName;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.reply.Reply#populate(com.google.inject.Injector, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	void populate(IocContainer iocContainer, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
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
		
		if(!Strings.isNullOrEmpty(charset)){
			response.setCharacterEncoding(charset);
		}
		String text = JSON.toJSONString(content);
		response.getWriter().write(text);
	}

}
