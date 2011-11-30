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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.glue.mvc.annotation.Delete;
import com.github.glue.mvc.annotation.Form;
import com.github.glue.mvc.annotation.Get;
import com.github.glue.mvc.annotation.Head;
import com.github.glue.mvc.annotation.Options;
import com.github.glue.mvc.annotation.Param;
import com.github.glue.mvc.annotation.Path;
import com.github.glue.mvc.annotation.Post;
import com.github.glue.mvc.annotation.Put;
import com.github.glue.mvc.annotation.Session;
import com.github.glue.mvc.annotation.Trace;
import com.github.glue.mvc.mapper.ObjectMapper;
import com.github.glue.mvc.mapper.RequestMapper;
import com.github.glue.mvc.mapper.ResponseMapper;
import com.github.glue.mvc.mapper.SessionMapper;
import com.github.glue.mvc.mapper.VarMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Eric.Lee
 *
 */
public class RequestDefinition{
	private Method target;
	private String charset;
	private Map<Field, VarMapper> fieldMappers;
	private VarMapper[] paramMappers;
	private UriPatternMatcher patternMatcher;
	
	public RequestDefinition(Method method) {
		this.target = method;
		this.charset = target.getAnnotation(Path.class).charset();
		initParamMappers();
		initFieldMappers();
	}




	private void initParamMappers(){
		Class[] parameterTypes = target.getParameterTypes();
		Annotation[][] parameterAnns = target.getParameterAnnotations();
		paramMappers = new VarMapper[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Class type = parameterTypes[i];
			Annotation[] anns = parameterAnns[i];
			paramMappers[i] = createMapper(type, anns);
		}
	}
	
	private void initFieldMappers(){
		fieldMappers = Maps.newHashMap();
		Class[] classes = getSuperClasses(target.getDeclaringClass());
		for (Class clazz : classes) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				Class type = field.getType();
				Annotation[] anns = type.getAnnotations();
				VarMapper mapper = createMapper(type, anns);
				if(!mapper.getClass().isAssignableFrom(ObjectMapper.class)){
					fieldMappers.put(field, mapper);
				}
			}
		}
		
		
	}
	
	public boolean matches(String uri,String method){
		boolean has = false;
		if("get".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Get.class);
		}else if("post".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Post.class);
		}else if("put".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Put.class);
		}else if("delete".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Delete.class);
		}else if("head".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Head.class);
		}else if("options".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Options.class);
		}else if("trace".equalsIgnoreCase(method)){
			has = target.isAnnotationPresent(Trace.class);
		}
		return patternMatcher.matches(uri) && has;
	}
	
	private VarMapper createMapper(Class type, Annotation[] anns){
		if(type.equals(HttpServletRequest.class) ){
			return new RequestMapper(type, anns);
		}else if(type.equals(HttpServletResponse.class)){
			return new ResponseMapper(type, anns);
		}else if(type.equals(HttpSession.class)){
			return new SessionMapper(type, anns);
		}else if(type.equals(Cookie[].class)){
//			return new CookiesMapper(type, anns);
		}else if(hasAnn(Form.class, anns)){
//			return new FormMapper(type, anns);
		}else if(hasAnn(Param.class, anns)){
//			return new ParamMapper(type, anns);
		}
		return new ObjectMapper(type, anns);
	}
	
	private boolean hasAnn(Class annClass, Annotation[] anns){
		for (Annotation ann : anns) {
			if(annClass.isInstance(ann)){
				return true;
			}
		}
		return false;
	}

	private Class[] getSuperClasses(Class type){
		List<Class> classList = Lists.newArrayList();
		if(!type.isAssignableFrom(Object.class)){
			classList.add(type);
			Class[] classes = getSuperClasses(type.getSuperclass());
			classList.addAll(Lists.newArrayList(classes));
		}
		return classList.toArray(new Class[classList.size()]);
		
	}
}
