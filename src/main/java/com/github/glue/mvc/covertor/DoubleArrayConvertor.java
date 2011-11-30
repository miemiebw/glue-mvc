/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class DoubleArrayConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null){
			return null;
		}
		
		double[] doubles = new double[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if(!Strings.isNullOrEmpty(parameters[i])){
				doubles[i] = Double.valueOf(parameters[i]);
			}
		}
		return doubles;
	}

}
