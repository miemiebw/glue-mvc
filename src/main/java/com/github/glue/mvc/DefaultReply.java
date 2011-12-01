/**
 * 
 */
package com.github.glue.mvc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class DefaultReply extends Reply {
	private Logger log = LoggerFactory.getLogger(getClass());
	String redirectUri;
	String charset;
	
	public DefaultReply with(String content) {
		super.with(content);
		return this;
	}
	public DefaultReply with(InputStream input){
		super.with(input);
		return this;
	}

	public DefaultReply redirect(String uri){
		this.redirectUri = uri;
		return this;
	}
	
	public DefaultReply charset(String charsetName){
		this.charset = charsetName;
		return this;
	}
	
	/* (non-Javadoc)
	 * @see com.github.glue.mvc.Reply#populate(com.google.inject.Injector, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
		
		if (null != redirectUri) {
			response.sendRedirect(redirectUri);
		    response.setStatus(status); // HACK to override whatever status the redirect sets.
		    return;
		}
		
		if (content instanceof InputStream) {
	        InputStream input = (InputStream) content;
	        try {
	        	byte[] buffer = new byte[4096];
//	            long count = 0;
	            int n = 0;
	            while (-1 != (n = input.read(buffer))) {
	            	response.getOutputStream().write(buffer, 0, n);
//	                count += n;
	            }
	        } finally {
	        	input.close();
	        }
	    }else if(content instanceof String) {
	    	  response.getWriter().write((String)content);
	    }else{
	    	log.warn("Don't support content: {}",content);
	    }
	}

}
