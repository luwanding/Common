package com.chyjr.platform.common.validator.def;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.chyjr.platform.common.validator.Email;


/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.def.EmailDef.java] 
 * @ClassName:    [EmailDef]  
 * @Description:  [邮箱校验实现]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class EmailDef implements ConstraintValidator<Email, String> {
	private String regex = 
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";;	
	
	@Override
	public void initialize(Email constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value != null && !"".equals(value)){
			Pattern p = Pattern.compile(regex);
			return p.matcher(value).matches();
		}
		return true;
	}
	
	
	
}
