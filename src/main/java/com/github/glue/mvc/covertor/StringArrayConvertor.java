/**
 * 
 */
package com.github.glue.mvc.covertor;

/**
 * @author eric
 *
 */
public class StringArrayConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		return parameters;
	}

}
