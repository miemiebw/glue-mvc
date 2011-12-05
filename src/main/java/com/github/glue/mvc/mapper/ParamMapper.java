/**
 * 
 */
package com.github.glue.mvc.mapper;

import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.RequestHandler;
import com.github.glue.mvc.annotation.Param;
import com.github.glue.mvc.covertor.ByteArrayConvertor;
import com.github.glue.mvc.covertor.TypeConvertor;
import com.github.glue.mvc.covertor.TypeConvertorFactory;
import com.google.common.collect.Lists;

/**
 * @author eric
 *
 */
public class ParamMapper extends VarMapper {
	private Logger log = LoggerFactory.getLogger(getClass());
	private TypeConvertor typeConvertor;
	private Param paramAnn;
	
	public ParamMapper(Class varType, Annotation[] annotations) {
		super(varType, annotations);
		typeConvertor = TypeConvertorFactory.getConvertor(type, annotations);
		for (Annotation ann : annotations) {
			if(ann.annotationType().equals(Param.class)){
				paramAnn =  (Param) ann;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.mapper.VarMapper#getVar(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public Object getVar(RequestHandler requestHandler) {
		
		if(paramAnn != null){
			if(typeConvertor != null){
				Object value = typeConvertor.convert(requestHandler.getParameter(paramAnn.value()));
				if(value != null){
					if(typeConvertor instanceof ByteArrayConvertor){
						log.debug("Request parameter: {}->{}(length)",paramAnn.value(),value == null ? null : ((byte[])value).length);
					}else{
						log.debug("Request parameter: {}->{}",paramAnn.value(),value);
					}
					return value;
				}
			}else{
				log.debug("Don't support request parameter type: {} , typeConvertor is null.",type);
			}
			
		}
		return null;
	}

}
