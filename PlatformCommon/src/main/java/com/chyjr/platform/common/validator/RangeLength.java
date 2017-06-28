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

import com.chyjr.platform.common.validator.def.RangeLengthDef;

/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.RangeLength.java] 
 * @ClassName:    [RangeLength]  
 * @Description:  [输入的长度范围接口]  
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
@Constraint(validatedBy = RangeLengthDef.class)
public @interface RangeLength {
	String message() default "{key}的长度应该在{min}的{max}之间。";
	/**最小长度，默认为0*/
	int min() default 0;
	/**最大长度，默认为Integer.MAX_VALUE*/
	int max() default Integer.MAX_VALUE;
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};
	
	String key() default "";
}
