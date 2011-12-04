/*
 * Copyright Eric Lee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.glue.mvc;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.mapper.VarMapper;

/**
 * @author Eric.Lee
 *
 */
public class RequestHandler {
	private Logger log = LoggerFactory.getLogger(getClass());
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDefinition definition;
	private Container container;
	
	public RequestHandler(HttpServletRequest request,
			HttpServletResponse response, RequestDefinition definition,Container container) {
		super();
		this.request = request;
		this.response = response;
		this.definition = definition;
		this.container = container;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	
	public void handle() throws Exception{
		Class actionClass = definition.getTarget().getDeclaringClass();
		Object action = container.getInstance(actionClass);
		
		VarMapper[] paramMappers = definition.getParamMappers();
		Object[] parameters = new Object[paramMappers.length];
		for (int i = 0; i < paramMappers.length; i++) {
			parameters[i] = paramMappers[i].getVar(this);
		}
		
		Map<Field, VarMapper> fieldMappers = definition.getFieldMappers();
		for (Map.Entry<Field, VarMapper> entry : fieldMappers.entrySet()) {
			Field field = entry.getKey();
			VarMapper varMapper = entry.getValue();
			Object value = varMapper.getVar(this);
			field.set(action, value);
		}
		
		
		Object executeResult = definition.getTarget().invoke(action, parameters);
		
		//renderView
		if(executeResult instanceof Reply){
			Reply reply = (Reply)executeResult;
			reply.populate(container, request, response);
		}else{
			log.warn("Return Object should be Reply instance.");
		}
	}
	
}
