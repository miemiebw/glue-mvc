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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;

/**
 * @author Eric.Lee
 *
 */
public class RequestHandler {
	private Logger log = LoggerFactory.getLogger(getClass());
	private HttpServletRequest request;
	private HttpServletResponse response;
	private RequestDefinition definition;
	private Injector injector;
	
	public RequestHandler(HttpServletRequest request,
			HttpServletResponse response, RequestDefinition definition,Injector injector) {
		super();
		this.request = request;
		this.response = response;
		this.definition = definition;
		this.injector = injector;
	//	init();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	
}
