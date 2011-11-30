/**
 * 
 */
package com.github.glue.mvc.covertor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author eric
 *
 */
public class InputStreamConvertor extends TypeConvertor {
	private Logger log = LoggerFactory.getLogger(getClass());
	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.Object)
	 */
	@Override
	public Object convert(Object value) {
		if (value instanceof FileItem) {
			FileItem fileItem = (FileItem) value;
			try {
				return fileItem.getInputStream();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}else if(value instanceof String[]){
			log.warn("Only support \"multipart/*\" contentType.");
		}
		return null;
	}

}
