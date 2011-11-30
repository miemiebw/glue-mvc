/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class FloatArrayConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null){
			return null;
		}
		float[] floats = new float[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if(!Strings.isNullOrEmpty(parameters[i])){
				floats[i] = Integer.valueOf(parameters[i]);
			}
		}
		return floats;
	}

}
