package com.chyjr.platform.common.validator.def;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.chyjr.platform.common.validator.NotNullAndEmpty;

/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.def.NotNullAndEmptyDef.java] 
 * @ClassName:    [NotNullAndEmptyDef]  
 * @Description:  [非空注解校验实现]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class NotNullAndEmptyDef implements ConstraintValidator<NotNullAndEmpty, String> {

	@Override
	public void initialize(NotNullAndEmpty constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(null != value && !"".equals(value.trim())){
			return true;
		}
		return false;
	}

	

}
