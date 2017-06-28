package com.chyjr.platform.common.validator.def;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.chyjr.platform.common.validator.Illegal;
/**
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.def.Illegal.java] 
 * @ClassName:    [Illegal]  
 * @Description:  [非法字符校验实现]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class IllegalDef implements ConstraintValidator<Illegal, String> {
	private String regex = "^[\\u0391-\\uFFE5\\w]+$";	// 只能包括中文字、英文字母、数字和下划线
	private Illegal i;
	
	@Override
	public void initialize(Illegal constraintAnnotation) {
		this.i = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isNotEmpty(value)){
			Pattern p = Pattern.compile(regex);
			if(!"".equals(i.regex())){
				p = Pattern.compile(i.regex());
			}
			return p.matcher(value).matches();
		}
		return true;
	}
	
	
}
