/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class DoubleWarpConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0 || Strings.isNullOrEmpty(parameters[0])){
			return null;
		}
		return Double.valueOf(parameters[0]);
	}

}
