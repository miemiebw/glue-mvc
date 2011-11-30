package com.github.glue.mvc.mapper;

import java.lang.annotation.Annotation;


import com.github.glue.mvc.RequestHandler;

public class RequestMapper extends VarMapper {

	public RequestMapper(Class varType, Annotation[] annotations) {
		super(varType, annotations);
	}

	@Override
	public Object getVar(RequestHandler requestHandler) {
		return requestHandler.getRequest();
	}


}
