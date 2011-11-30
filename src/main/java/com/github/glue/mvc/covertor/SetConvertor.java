/**
 * 
 */
package com.github.glue.mvc.covertor;

import com.google.common.collect.Sets;

/**
 * @author eric
 *
 */
public class SetConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0){
			return null;
		}
		return Sets.newHashSet(parameters);
	}

}
