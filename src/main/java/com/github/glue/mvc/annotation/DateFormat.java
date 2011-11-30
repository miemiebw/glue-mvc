/**
 * 
 */
package com.github.glue.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Eric.Lee
 *
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME) 
public @interface DateFormat {
	String value() default "yyyy-MM-dd HH:mm:ss";
}
