/*
 * Copyright Eric Lee.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.glue.mvc.general;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.glue.mvc.Container;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author Eric.Lee
 *
 */
public class DefaultContainer implements Container {
	private static final Logger log = LoggerFactory.getLogger( DefaultContainer.class );
	private Map<String, Object> context = Maps.newLinkedHashMap();

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstance(java.lang.Class)
	 */
	@Override
	public <T> T getInstance(Class<T> type) {
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			Object instance = entry.getValue();
			if(type.isInstance(instance)){
				return (T) instance;
			}
		}
		try {
			return type.newInstance();
		} catch (Exception e) {
		}
		return null;
	}

	
	public <T> Set<T> getInstances(Class<T> type){
		Set<T> instances = Sets.newLinkedHashSet();
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			Object instance = entry.getValue();
			if(type.isInstance(instance)){
				instances.add((T) instance) ;
			}
		}
		return instances;
	}

	


	public void bind(String id, Object instance){
		context.put(id, instance);
		log.debug("bind id:{}, instance:{}",id,instance);
	}

}
