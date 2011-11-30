/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class LongArrayConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null){
			return null;
		}
		
		long[] longs = new long[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if(!Strings.isNullOrEmpty(parameters[i])){
				longs[i] = Long.valueOf(parameters[i]);
			}
		}
		return longs;
	}

}
