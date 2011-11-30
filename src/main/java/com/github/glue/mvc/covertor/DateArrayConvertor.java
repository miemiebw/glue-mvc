/**
 * 
 */
package com.github.glue.mvc.covertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Strings;

/**
 * @author eric
 *
 */
public class DateArrayConvertor extends TypeConvertor<String[]> {
	private String dateFormatStyle;
	
	public DateArrayConvertor(String dateFormatStyle) {
		super();
		this.dateFormatStyle = dateFormatStyle;
	}
	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.String[])
	 */
	@Override
	public Object convert(String[] parameters) {
		if(parameters == null){
			return null;
		}
		
		Date[] dates = new Date[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			SimpleDateFormat format = new SimpleDateFormat(dateFormatStyle);
			try {
				dates[i] = format.parse(parameters[i]);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
		return dates;
	}

}
