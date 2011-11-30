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
public class ResponseMapper extends VarMapper {

	public ResponseMapper(Class varType, Annotation[] annotations) {
		super(varType, annotations);
	}

	@Override
	public Object getVar(RequestHandler requestHandler) {
		return requestHandler.getResponse();
	}

}
