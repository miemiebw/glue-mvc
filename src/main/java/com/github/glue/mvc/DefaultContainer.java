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
package com.github.glue.mvc;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @author Eric.Lee
 *
 */
public class DefaultContainer implements IocContainer {
	private static final Logger log = LoggerFactory.getLogger( DefaultContainer.class );
	private Map<String, Object> context = Maps.newHashMap();

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstance(java.lang.Class)
	 */
	@Override
	public <T> T getInstance(Class<T> type) {
		try {
			for (Map.Entry<String, Object> entry : context.entrySet()) {
				Object instance = entry.getValue();
				if(type.isInstance(instance)){
					return (T) instance;
				}
			}
			return type.newInstance();
		} catch (Exception e) {
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#inject(java.lang.Object)
	 */
	@Override
	public void inject(Object o) {

	}

	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstanceNames(java.lang.Class)
	 */
	@Override
	public Set<String> getInstanceNames(Class<?> type) {
		Set<String> viewResolverNames = Sets.newHashSet();
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			Object instance = entry.getValue();
			if(type.isInstance(instance)){
				viewResolverNames.add(entry.getKey());
			}
		}
		return viewResolverNames;
	}

	public void binding(String id, Object instance){
		context.put(id, instance);
		log.debug("binding id:{}, instance:{}",id,instance);
	}


	/* (non-Javadoc)
	 * @see com.github.glue.mvc.IocContainer#getInstance(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> T getInstance(Class<T> type, String name) {
		Object instance = context.get(name);
		if(type.isInstance(instance)){
			return (T) instance;
		}
		return null;
	}
}
