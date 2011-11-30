/**
 * 
 */
package com.github.glue.mvc.mapper;

import java.lang.annotation.Annotation;

import com.github.glue.mvc.RequestHandler;

/**
 * @author Eric
 *
 */
public class ObjectMapper extends VarMapper {

	public ObjectMapper(Class varType, Annotation[] annotations) {
		super(varType, annotations);
	}

	@Override
	public Object getVar(RequestHandler requestHandler) {
		// TODO Auto-generated method stub
		return null;
	}

}
