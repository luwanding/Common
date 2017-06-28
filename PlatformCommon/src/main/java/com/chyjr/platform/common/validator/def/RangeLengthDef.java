package com.chyjr.platform.common.validator.def;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.chyjr.platform.common.validator.RangeLength;


/**
 * 验证是否为手机号码或则电话号码。只有在非空的情况下才进行校验，为空则返回true。
 * 如果只需要校验手机号码，设置isMobile 为true，手机校验包含了13*、15*、18*的号码段。
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.def.RangeLengthDef.java] 
 * @ClassName:    [RangeLengthDef]  
 * @Description:  [非空注解校验实现]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class RangeLengthDef implements ConstraintValidator<RangeLength, String> {
	private RangeLength range;
	
	@Override
	public void initialize(RangeLength constraintAnnotation) {
		range = constraintAnnotation;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)){
			return true;
		}
		if(value.length() <= range.max() && value.length() >= range.min()){
			return true;
		}
		return false;
	}

	

}
