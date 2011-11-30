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
public class ByteArrayConvertor extends TypeConvertor<Object> {
	private Logger log = LoggerFactory.getLogger(getClass());
	/* (non-Javadoc)
	 * @see com.github.glue.mvc.covertor.TypeConvertor#convert(java.lang.Object)
	 */
	@Override
	public Object convert(Object value) {
		if (value instanceof FileItem) {
			FileItem fileItem = (FileItem) value;
			try {
				InputStream input = fileItem.getInputStream();
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				byte[] buff = new byte[100];
				int rc = 0;
				while((rc = input.read(buff, 0, 100)) > 0){
					swapStream.write(buff, 0, rc);
				}
				swapStream.flush();
				byte[] bytes = swapStream.toByteArray();
				swapStream.close();
				input.close();
				return bytes;
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}else if(value instanceof String[]){
			log.warn("Only support \"multipart/*\" contentType.");
		}
		return null;
	}

}
