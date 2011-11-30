/**
 * 
 */
package com.github.glue.mvc.covertor;

/**
 * @author eric
 *
 */
public class BooleanArrayConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null){
			return null;
		}
		boolean[] booleans = new boolean[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			booleans[i] = Boolean.valueOf(parameters[i]);
		}
		return booleans;
	}

}
