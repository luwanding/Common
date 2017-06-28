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

import com.chyjr.platform.common.validator.def.DigitDef;

/**
 * <b>数字校验。</b><br/>
 * 如果可以为负数，设置ableNegative为true，默认为false。<br/>
 * 如果可以为小数，设置ableFloat为true，默认为false。<br/>
 * 设置保留小数点后位数，设置floatPoint为需要保留的位数，默认为2，即保留2位小数。如果小于0表示不限制保留的位数，大于0表示限制。
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.Digit.java] 
 * @ClassName:    [Digit]  
 * @Description:  [数字校验]  
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
@Constraint(validatedBy = DigitDef.class)
public @interface Digit {
	String message() default "{key}请输入数字";
	/** 是否允许为负数 默认为：false*/
	boolean ableNegative() default false;
	/** 是否允许为小数 默认为：false*/
	boolean ableFloat() default false;
	/** 小数点保留位数，前提是开启ableFloat。 默认为：2位*/
	int floatPoint() default 2;
	
	int minValue() default Integer.MIN_VALUE;
	
	int maxValue() default Integer.MAX_VALUE;

	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default {};
	
	String key() default "";
}
