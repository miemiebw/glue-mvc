/**
 * 
 */
package com.github.glue.mvc.mapper;

import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.RequestHandler;
import com.github.glue.mvc.annotation.Param;
import com.github.glue.mvc.annotation.ServletContext;
import com.github.glue.mvc.annotation.Session;
import com.github.glue.mvc.covertor.ByteArrayConvertor;
import com.github.glue.mvc.covertor.TypeConvertor;
import com.github.glue.mvc.covertor.TypeConvertorFactory;
import com.google.common.collect.Lists;

/**
 * @author eric
 *
 */
public class ServletContextAttrMapper extends VarMapper {
	private Logger log = LoggerFactory.getLogger(getClass());
	private ServletContext contextAnn;
	
	public ServletContextAttrMapper(Class varType, Annotation[] annotations) {
		super(varType, annotations);
		for (Annotation ann : annotations) {
			if(ann.annotationType().equals(Param.class)){
				contextAnn =  (ServletContext) ann;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.mapper.VarMapper#getVar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Object getVar(RequestHandler requestHandler) {
		
		if(contextAnn != null){
			javax.servlet.ServletContext servletContext = requestHandler.getRequest().getSession().getServletContext();
			Object value = servletContext.getAttribute(contextAnn.value());
			log.debug("ServletContext attribute: {}->{}",contextAnn.value(),value);
			return value;
		}
		return null;
	}

}
