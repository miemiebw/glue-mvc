package com.github.glue.util;

import java.beans.Introspector;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class BeanUtil {

	public static Map<String, Object> mapProperties(Object bean) throws Exception {
	    Map<String, Object> properties = new HashMap<String, Object>();
	    for (Method method : bean.getClass().getDeclaredMethods()) {
	        if (Modifier.isPublic(method.getModifiers())
	            && method.getParameterTypes().length == 0
	            && method.getReturnType() != void.class
	            && method.getName().matches("^(get|is).+")
	        ) {
	            String name = method.getName().replaceAll("^(get|is)", "");
//	            name = Character.toLowerCase(name.charAt(0)) + (name.length() > 1 ? name.substring(1) : "");
	            name = Introspector.decapitalize(name);
	            Object value = method.invoke(bean);
	            properties.put(name, value);
	        }
	    }
	    return properties;
	}
	
	public static Method[] getSetterMethods(Class clazz){
		List<Method> methods = Lists.newArrayList();
		for (Method method : clazz.getDeclaredMethods()) {
	        if (Modifier.isPublic(method.getModifiers())
	            && method.getName().matches("^(set|is).+")
	        ) {
	            methods.add(method);
	        }
	    }
		
		return methods.toArray(new Method[methods.size()]);
	}
	
	public static String getPropName(Method method){
		String name = null;
		if (Modifier.isPublic(method.getModifiers())
	        && method.getName().matches("^(set).+")
	    ){
			name = method.getName().replaceAll("^(set)", "");
		}else if(Modifier.isPublic(method.getModifiers())
		        && method.getName().matches("^(get|is).+")){
			name = method.getName().replaceAll("^(get|is)", "");
		}
		if(!Strings.isNullOrEmpty(name)){
			name = name.substring(0, 1).toLowerCase() + name.substring(1);
		}
		return name;
	}
	
	
}
