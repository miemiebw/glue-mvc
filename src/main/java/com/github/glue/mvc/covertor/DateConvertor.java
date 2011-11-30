/**
 * 
 */
package com.github.glue.mvc.covertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class DateConvertor extends TypeConvertor<String[]> {
	private String dateFormatStyle;
	
	public DateConvertor(String dateFormatStyle) {
		super();
		this.dateFormatStyle = dateFormatStyle;
	}


	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0 || Strings.isNullOrEmpty(parameters[0])){
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat(dateFormatStyle);
		try {
			return format.parse(parameters[0]);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
