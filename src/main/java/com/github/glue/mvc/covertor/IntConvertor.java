package com.github.glue.mvc.covertor;

import com.google.common.base.Strings;

public class IntConvertor extends TypeConvertor<String[]> {

	@Override
	public Object convert(String[] parameters) {
		if(parameters == null || parameters.length == 0 || Strings.isNullOrEmpty(parameters[0])){
			return 0;
		}
		return Integer.valueOf(parameters[0]);
	}

}
