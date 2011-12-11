/**
 * 
 */
package com.github.glue.mvc.view.support;

import java.util.LinkedHashMap;
import java.util.Map;

import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.expression.IStandardExpressionEvaluator;
import org.thymeleaf.standard.expression.OgnlExpressionEvaluator;
import org.thymeleaf.standard.expression.StandardExpressionExecutor;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;

/**
 * @author Eric
 *
 */
public class CustomStandardDialect extends StandardDialect {
	IStandardExpressionEvaluator standardExpressionEvaluator = OgnlExpressionEvaluator.INSTANCE;
	
	public CustomStandardDialect() {
		
	}
	
	
	
	public CustomStandardDialect(
			IStandardExpressionEvaluator standardExpressionEvaluator) {
		super();
		this.standardExpressionEvaluator = standardExpressionEvaluator;
	}



	@Override
	public Map<String, Object> getExecutionAttributes() {
		final StandardExpressionExecutor executor = StandardExpressionProcessor.createStandardExpressionExecutor(standardExpressionEvaluator);
        final StandardExpressionParser parser = StandardExpressionProcessor.createStandardExpressionParser(executor);
        
        final Map<String,Object> executionAttributes = new LinkedHashMap<String, Object>();
        executionAttributes.put(
                StandardExpressionProcessor.STANDARD_EXPRESSION_EXECUTOR_ATTRIBUTE_NAME, executor);
        executionAttributes.put(
                StandardExpressionProcessor.STANDARD_EXPRESSION_PARSER_ATTRIBUTE_NAME, parser);
        
        return executionAttributes;
	}

}
