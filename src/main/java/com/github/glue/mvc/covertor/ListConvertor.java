/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.collect.Lists;

/**
 * @author eric
 *
 */
public class ListConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0){
			return null;
		}
		return Lists.newArrayList(parameters);
	}

}
