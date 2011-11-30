/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class LongWarpArrayConvertor extends TypeConvertor<String[]>{

	@Override
	public Object convert(String[] parameters) {
		if(parameters == null){
			return null;
		}
		Long[] longs = new Long[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if(!Strings.isNullOrEmpty(parameters[i])){
				longs[i] = Long.valueOf(parameters[i]);
			}
		}
		return longs;
	}

}
