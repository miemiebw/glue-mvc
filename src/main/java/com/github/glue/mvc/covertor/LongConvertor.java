package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

public class LongConvertor extends TypeConvertor<String[]>{

	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0 || Strings.isNullOrEmpty(parameters[0])){
			return 0L;
		}
		return Long.valueOf(parameters[0]);
	}

}
