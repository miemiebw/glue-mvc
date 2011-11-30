/**
 * 
 */
package com.github.glue.mvc.covertor;

/**
 * @author eric
 *
 */
public class StringConvertor extends TypeConvertor<String[]> {

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0){
			return null;
		}
		return parameters[0];
	}

}
