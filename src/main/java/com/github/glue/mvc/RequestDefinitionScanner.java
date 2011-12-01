/**
 * 
 */
package com.github.glue.mvc;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.annotation.Action;
import com.github.glue.mvc.annotation.Path;
import com.github.glue.util.ResolverUtil;
import com.google.common.collect.Lists;

/**
 * @author Ecric.Lee
 *
 */
public class RequestDefinitionScanner {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String[] packages;

	public RequestDefinitionScanner(String... packages) {
		super();
		this.packages = packages;
	}
	
	public List<RequestDefinition> scan(){
		List<RequestDefinition> executors = Lists.newArrayList();
		ResolverUtil resolverUtil = new ResolverUtil();
		Set<Class> classes = resolverUtil.findAnnotated(Action.class, packages).getClasses();
		
		
		for (Class clz : classes) {
			Method[] methods = clz.getMethods();
			for (Method method : methods) {
				RequestDefinition executor = null;
				Path pathAnn = method.getAnnotation(Path.class);
				if(pathAnn != null){
					executor = new RequestDefinition(method);
					log.debug("RequestDefinition: {}"," value="+pathAnn.value()+", target="+method.getDeclaringClass().getName()+"."+method.getName());
				}
				
				if(executor != null){
					executors.add(executor);
				}
			}
		}
		
		return executors;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestDefinitionScanner [packages=");
		builder.append(Arrays.toString(packages));
		builder.append("]");
		return builder.toString();
	}
	
	
}
