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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.glue.mvc.annotation.Action;
import com.github.glue.mvc.annotation.Cookie;
import com.github.glue.mvc.annotation.Delete;
import com.github.glue.mvc.annotation.Form;
import com.github.glue.mvc.annotation.Get;
import com.github.glue.mvc.annotation.Head;
import com.github.glue.mvc.annotation.Header;
import com.github.glue.mvc.annotation.Options;
import com.github.glue.mvc.annotation.Param;
import com.github.glue.mvc.annotation.Path;
import com.github.glue.mvc.annotation.Post;
import com.github.glue.mvc.annotation.Put;
import com.github.glue.mvc.annotation.ServletContext;
import com.github.glue.mvc.annotation.Session;
import com.github.glue.mvc.annotation.Trace;
import com.github.glue.mvc.mapper.FormMapper;
import com.github.glue.mvc.mapper.ObjectMapper;
import com.github.glue.mvc.mapper.ParamMapper;
import com.github.glue.mvc.mapper.RequestMapper;
import com.github.glue.mvc.mapper.ResponseMapper;
import com.github.glue.mvc.mapper.ServletContextAttrMapper;
import com.github.glue.mvc.mapper.SessionAttrMapper;
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
		
		Class declaringClz = target.getDeclaringClass();
		Action actionAnn = (Action) declaringClz.getAnnotation(Action.class);
		Path pathAnn = target.getAnnotation(Path.class);
		patternMatcher = new ServletStyleUriPatternMatcher(actionAnn.value()+pathAnn.value());
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
				Annotation[] anns = field.getAnnotations();
				VarMapper mapper = createMapper(type, anns);
				if(!mapper.getClass().isAssignableFrom(ObjectMapper.class)){
					field.setAccessible(true);
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
		}else if(hasAnn(Form.class, anns)){
			return new FormMapper(type, anns);
		}else if(hasAnn(Param.class, anns)){
			return new ParamMapper(type, anns);
		}else if(hasAnn(Header.class, anns)){

		}else if(hasAnn(Cookie.class, anns)){

		}else if(hasAnn(Session.class, anns)){
			return new SessionAttrMapper(type, anns);
		}else if(hasAnn(ServletContext.class, anns)){
			return new ServletContextAttrMapper(type, anns);
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




	public Method getTarget() {
		return target;
	}
	
	public Map<Field, VarMapper> getFieldMappers() {
		return fieldMappers;
	}

	public VarMapper[] getParamMappers() {
		return paramMappers;
	}
	


	private static class ServletStyleUriPatternMatcher implements UriPatternMatcher {
	    @Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("ServletStyleUriPatternMatcher [pattern=");
			builder.append(pattern);
			builder.append(", patternKind=");
			builder.append(patternKind);
			builder.append("]");
			return builder.toString();
		}

		private final String pattern;
	    private final Kind patternKind;

	    private static enum Kind { PREFIX, SUFFIX, LITERAL, }

	    public ServletStyleUriPatternMatcher(String pattern) {
	      if (pattern.startsWith("*")) {
	        this.pattern = pattern.substring(1);
	        this.patternKind = Kind.PREFIX;
	      } else if (pattern.endsWith("*")) {
	        this.pattern = pattern.substring(0, pattern.length() - 1);
	        this.patternKind = Kind.SUFFIX;
	      } else {
	        this.pattern = pattern;
	        this.patternKind = Kind.LITERAL;
	      }
	    }

	    public boolean matches(String uri) {
	      if (null == uri) {
	        return false;
	      }

	      if (patternKind == Kind.PREFIX) {
	        return uri.endsWith(pattern);
	      } else if (patternKind == Kind.SUFFIX) {
	        return uri.startsWith(pattern);
	      }

	      //else treat as a literal
	      return pattern.equals(uri);
	    }

	    public String extractPath(String path) {
	      if (patternKind == Kind.PREFIX) {
	        return null;
	      } else if (patternKind == Kind.SUFFIX) {
	        String extract = pattern;

	        //trim the trailing '/'
	        if (extract.endsWith("/")) {
	          extract = extract.substring(0, extract.length() - 1);
	        }

	        return extract;
	      }

	      //else treat as literal
	      return path;
	    }
	    
	  }

	  /**
	   * Matches URIs using a regular expression.
	   *
	   * @author dhanji@gmail.com (Dhanji R. Prasanna)
	   */
	  private static class RegexUriPatternMatcher implements UriPatternMatcher {
	    private final Pattern pattern;

	    public RegexUriPatternMatcher(String pattern) {
	      this.pattern = Pattern.compile(pattern);
	    }

	    public boolean matches(String uri) {
	      return null != uri && this.pattern.matcher(uri).matches();
	    }

	    public String extractPath(String path) {
	      Matcher matcher = pattern.matcher(path);
	      if (matcher.matches() && matcher.groupCount() >= 1) {

	        // Try to capture the everything before the regex begins to match
	        // the path. This is a rough approximation to try and get parity
	        // with the servlet style mapping where the path is a capture of
	        // the URI before the wildcard.
	        int end = matcher.start(1);
	        if (end < path.length()) {
	          return path.substring(0, end);
	        }
	      }
	      return null;
	    }

	  }
	
}
