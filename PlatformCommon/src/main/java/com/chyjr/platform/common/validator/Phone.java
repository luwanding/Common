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

import com.chyjr.platform.common.validator.def.PhoneDef;


/**
 * 验证是否为手机号码或则电话号码。只有在非空的情况下才进行校验，为空则返回true。
 * 如果只需要校验手机号码，设置isMobile 为true，手机校验包含了13*、15*、18*的号码段。
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.Phone.java] 
 * @ClassName:    [Phone]  
 * @Description:  [手机格式校验接口]  
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
@Constraint(validatedBy = {PhoneDef.class})
public @interface Phone {
	String message() default "{key}号码格式不正确";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default {};
	
	String key() default "";
	
	boolean isMobile() default false;
}
