package com.chyjr.platform.common.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.chyjr.platform.common.validator.def.IllegalDef;

/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.Illegal.java] 
 * @ClassName:    [Illegal]  
 * @Description:  [非法字符校验]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IllegalDef.class)
public @interface Illegal {
	String message() default "{key}包含非法字符";

	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default {};
	
	String regex() default "";
	
	String key() default "";
}
