/**
 * 
 */
package com.github.glue.mvc.mapper;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.RequestHandler;
import com.github.glue.mvc.annotation.Form;
import com.github.glue.mvc.covertor.ByteArrayConvertor;
import com.github.glue.mvc.covertor.TypeConvertor;
import com.github.glue.mvc.covertor.TypeConvertorFactory;
import com.github.glue.util.BeanUtil;
import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class FormMapper extends VarMapper {
	private Logger log = LoggerFactory.getLogger(getClass());
	private Form formAnn;
	
	public FormMapper(Class type, Annotation[] annotations) {
		super(type, annotations);
		for (Annotation ann : annotations) {
			if(ann.annotationType().equals(Form.class)){
				formAnn =  (Form) ann;
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.mapper.VarMapper#getVar(com.github.glue.mvc.RequestHandler)
	 */
	@Override
	public Object getVar(RequestHandler requestHandler) {
		
		return mapBean(formAnn.value(),type,requestHandler);
	}

	private Object mapBean(String path, Class beanClazz, RequestHandler requestHandler){
		Object bean = null;
		try {
			bean = (beanClazz).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		boolean foundValue = false;
		Method[] setters = BeanUtil.getSetterMethods(bean.getClass());
		for (Method method : setters) {
			
			Class[] paramTypes = method.getParameterTypes();
			if(paramTypes.length != 1){
				//TODO warn log
				continue;
			}
			
			String name = BeanUtil.getPropName(method);
			if(!Strings.isNullOrEmpty(path)){
				name = path+"."+name;
			}
			
			Class paramType = paramTypes[0];
			if(!isNotBean(paramType)){
				Object paramValue = mapBean(name, paramType, requestHandler);
				try {
					method.invoke(bean, paramValue);
					continue;
				}  catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			
			Object value = null;
			TypeConvertor convertor = TypeConvertorFactory.getConvertor(paramType, annotations);
			if(convertor != null){
				value = convertor.convert(requestHandler.getParameter(name));
				if(convertor instanceof ByteArrayConvertor){
					log.debug("Request parameter: {}->{}(length)",name,value == null ? null : ((byte[])value).length);
				}else{
					log.debug("Request parameter: {}->{}",name,value);
				}
			}
			try {
				method.invoke(bean, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			foundValue = true;
			
		}
		if(foundValue){
			return bean;
		}
		return null;
	}
	
	private boolean isNotBean(Class clazz){
		if(clazz.equals(int.class)){
			return true;
			
		}else if(clazz.equals(int[].class)){
			return true;
			
		}else if(clazz.equals(Integer.class)){
			return true;
			
		}else if(clazz.equals(Integer[].class)){
			return true;
			
		}else if(clazz.equals(long.class)){
			return true;
			
		}else if(clazz.equals(long[].class)){
			return true;
			
		}else if(clazz.equals(Long.class)){
			return true;
			
		}else if(clazz.equals(Long[].class)){
			return true;
			
		}else if(clazz.equals(float.class)){
			return true;
			
		}else if(clazz.equals(float[].class)){
			return true;
			
		}else if(clazz.equals(Float.class)){
			return true;
			
		}else if(clazz.equals(Float[].class)){
			return true;
			
		}else if(clazz.equals(double.class)){
			return true;
			
		}else if(clazz.equals(double[].class)){
			return true;
			
		}else if(clazz.equals(Double.class)){
			return true;
			
		}else if(clazz.equals(Double[].class)){
			return true;
			
		}else if(clazz.equals(boolean.class)){
			return true;
			
		}else if(clazz.equals(boolean[].class)){
			return true;
			
		}else if(clazz.equals(Boolean.class)){
			return true;
			
		}else if(clazz.equals(Boolean[].class)){
			return true;
			
		}else if(clazz.equals(String.class)){
			return true;
			
		}else if(clazz.equals(String[].class)){
			return true;
			
		}else if(clazz.equals(Date.class)){
			return true;
			
		}else if(clazz.equals(Date[].class)){
			return true;
		}else if(clazz.equals(List.class)){
			return true;
		}else if(clazz.equals(Set.class)){
			return true;
		}else if(clazz.equals(byte[].class)){
			return true;
		}else if(clazz.equals(InputStream.class)){
			return true;
		}
		
		return false;
	}
}
