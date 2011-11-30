/**
 * 
 */
package com.github.glue.mvc.mapper;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import com.github.glue.mvc.RequestHandler;



/**
 * @author Eric.Lee
 *
 */
public abstract class VarMapper {
	protected Annotation[] annotations;
	protected Class type;
	
	public VarMapper(Class type, Annotation[] annotations) {
		super();
		this.type = type;
		this.annotations = annotations;
	}
	
	
	public abstract Object getVar(RequestHandler requestHandler);


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName()+" [type=");
		builder.append(type.getName());
		builder.append(", annotations=");
		builder.append(Arrays.toString(annotations));
		builder.append("]");
		return builder.toString();
	}
	

	
}
