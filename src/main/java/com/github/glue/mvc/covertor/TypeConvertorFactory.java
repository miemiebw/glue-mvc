/**
 * 
 */
package com.github.glue.mvc.covertor;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.glue.mvc.annotation.DateFormat;
import com.google.common.collect.Maps;

/**
 * @author eric
 *
 */
public class TypeConvertorFactory {
	
	public static TypeConvertor getConvertor(Class type,Annotation[] annotations){
		
		if(type.equals(int.class)){
			return new IntConvertor();
			
		}else if(type.equals(int[].class)){
			return new IntArrayConvertor();
			
		}else if(type.equals(Integer.class)){
			return new IntegerConvertor();
			
		}else if(type.equals(Integer[].class)){
			return new IntegerArrayConvertor();
			
		}else if(type.equals(long.class)){
			return new LongConvertor();
			
		}else if(type.equals(long[].class)){
			return new LongArrayConvertor();
			
		}else if(type.equals(Long.class)){
			return new LongWarpConvertor();
			
		}else if(type.equals(Long[].class)){
			return new LongWarpArrayConvertor();
			
		}else if(type.equals(float.class)){
			return new FloatConvertor();
			
		}else if(type.equals(float[].class)){
			return new FloatArrayConvertor();
			
		}else if(type.equals(Float.class)){
			return new FloatWarpConvertor();
			
		}else if(type.equals(Float[].class)){
			return new FloatWarpArrayConvertor();
			
		}else if(type.equals(double.class)){
			return new DoubleConvertor();
			
		}else if(type.equals(double[].class)){
			return new DoubleArrayConvertor();
			
		}else if(type.equals(Double.class)){
			return new DoubleWarpConvertor();
			
		}else if(type.equals(Double[].class)){
			return new DoubleWarpArrayConvertor();
			
		}else if(type.equals(boolean.class)){
			return new BooleanConvertor();
			
		}else if(type.equals(boolean[].class)){
			return new BooleanArrayConvertor();
			
		}else if(type.equals(Boolean.class)){
			return new BooleanWarpConvertor();
			
		}else if(type.equals(Boolean[].class)){
			return new BooleanWarpArrayConvertor();
			
		}else if(type.equals(String.class)){
			return new StringConvertor();
			
		}else if(type.equals(String[].class)){
			return new StringArrayConvertor();
			
		}else if(type.equals(Date.class)){
			String dateFormatStyle = "yyyy-MM-dd HH:mm:ss";
			for (Annotation ann : annotations) {
				if(ann.annotationType().equals(DateFormat.class)){
					DateFormat dateFormat =  (DateFormat) ann;
					dateFormatStyle = dateFormat.value();
				}
			}
			return new DateConvertor(dateFormatStyle);
			
		}else if(type.equals(Date[].class)){
			String dateFormatStyle = "yyyy-MM-dd HH:mm:ss";
			for (Annotation ann : annotations) {
				if(ann.annotationType().equals(DateFormat.class)){
					DateFormat dateFormat =  (DateFormat) ann;
					dateFormatStyle = dateFormat.value();
				}
			}
			return new DateArrayConvertor(dateFormatStyle);
		}else if(type.equals(List.class)){
			return new ListConvertor();
		}else if(type.equals(Set.class)){
			return new SetConvertor();
		}else if(type.equals(byte[].class)){
			return new ByteArrayConvertor();
		}else if(type.equals(InputStream.class)){
			return new InputStreamConvertor();
		}
		
		return null;
	}
}
