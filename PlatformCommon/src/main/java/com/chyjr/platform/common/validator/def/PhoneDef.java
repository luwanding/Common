package com.chyjr.platform.common.validator.def;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.chyjr.platform.common.validator.Phone;


/**
 * 验证是否为手机号码或则电话号码。只有在非空的情况下才进行校验，为空则返回true。
 * 如果只需要校验手机号码，设置isMobile 为true，手机校验包含了13*、15*、18*的号码段。
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.def.PhoneDef.java] 
 * @ClassName:    [PhoneDef]  
 * @Description:  [手机格式校验实现]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class PhoneDef implements ConstraintValidator<Phone, String> {
	private String mobileRegexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]))+\\d{8})$";
	private String poneRegexp = "^(\\d{3,4}-?)?\\d{7,9}$";
	private Phone p;
	
	@Override
	public void initialize(Phone constraintAnnotation) {
		this.p = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isNotEmpty(value)){
			Pattern p = Pattern.compile(poneRegexp);
			if(this.p.isMobile()){	// 只校验手机号码
				p = Pattern.compile(mobileRegexp);
			}
			
			return p.matcher(value).matches();
		}
		return true;
	}
}
