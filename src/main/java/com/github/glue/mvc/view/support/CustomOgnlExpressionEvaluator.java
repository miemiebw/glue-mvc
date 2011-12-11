/**
 * 
 */


/*
 * =============================================================================
 * 
 *   Copyright (c) 2011, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package com.github.glue.mvc.view.support;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import ognl.OgnlException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.Arguments;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.exceptions.ExpressionEvaluationException;
import org.thymeleaf.expression.ExpressionEvaluatorObjects;
import org.thymeleaf.standard.expression.IStandardExpressionEvaluator;
import org.thymeleaf.templateresolver.TemplateResolution;

/**
 * 
 * @author Daniel Fern&aacute;ndez
 * 
 * @since 1.1
 *
 */
public class CustomOgnlExpressionEvaluator 
        implements IStandardExpressionEvaluator {
    

    public static final CustomOgnlExpressionEvaluator INSTANCE = new CustomOgnlExpressionEvaluator();

    
    private static final Logger logger = LoggerFactory.getLogger(CustomOgnlExpressionEvaluator.class);
    
    private static final int CACHE_MAX_SIZE = 100;
    
    private static final Map<String,SoftReference<Object>> CACHE =
        Collections.synchronizedMap(new CacheMap(CACHE_MAX_SIZE));
    

    
    


    
    public final Object evaluate(final Arguments arguments, 
            final TemplateResolution templateResolution, final String expression, final Object root) {
       
        try {

            if (logger.isTraceEnabled()) {
                logger.trace("[THYMELEAF][{}] OGNL expression: evaluating expression \"{}\" on target", TemplateEngine.threadIndex(), expression);
            }
            
            Object expressionTree = null;
            SoftReference<Object> expressionTreeRef = CACHE.get(expression);
            if (expressionTreeRef != null) {
                expressionTree = expressionTreeRef.get();
            }
            if (expressionTree == null) {
                expressionTree = ognl.Ognl.parseExpression(expression);
                CACHE.put(expression, new SoftReference<Object>(expressionTree));
            }

            final Map<String,Object> contextVariables = 
                new LinkedHashMap<String,Object>(
                        ExpressionEvaluatorObjects.computeUtilityEvaluationObjects(arguments, templateResolution));
            
            final Map<String,Object> additionalContextVariables =
                computeAdditionalContextVariables(arguments, templateResolution);
            if (additionalContextVariables != null) {
                contextVariables.putAll(additionalContextVariables);
            }
            
            return ognl.Ognl.getValue(expressionTree, contextVariables, root);
            
        } catch (final OgnlException e) {
        	logger.warn(e.getMessage());
            //throw new ExpressionEvaluationException("Exception evaluating OGNL expression", e);
        }
        return "";
    }



    
    /*
     * Meant to be overwritten
     */
    @SuppressWarnings("unused")
    protected Map<String,Object> computeAdditionalContextVariables(
            final Arguments arguments, final TemplateResolution templateResolution) {
        return variables;
    }
    
    private Map<String,Object> variables = new HashMap<String, Object>();
    public void addContextVariable(String variableName,Object varible){
    	variables.put(variableName, varible);
    }
    
    
    
    public CustomOgnlExpressionEvaluator() {
        super();
    }

    
    
    
    
    final static class CacheMap extends LinkedHashMap<String,SoftReference<Object>> {
        
        private static final long serialVersionUID = 4921099038104722137L;
        
        private final int maxSize;

        public CacheMap(final int maxSize) {
            super(16, 0.75f, true);
            this.maxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(final Entry<String, SoftReference<Object>> eldest) {
            return size() > this.maxSize;
        }
        
        
    }


    @Override
    public String toString() {
        return "OGNL";
    }
    
}
