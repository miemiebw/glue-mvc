/**
 * 
 */
package com.github.glue.mvc.covertor;

/**
 * @author eric
 *
 */
public abstract class TypeConvertor<T> {
	
	public abstract Object convert(T parameters);
}
