package com.chyjr.platform.common.validator.def;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.chyjr.platform.common.validator.Digit;


/**
 * <b>数字校验。</b><br/>
 * 如果可以为负数，设置ableNegative为true，默认为false。<br/>
 * 如果可以为小数，设置ableFloat为true，默认为false。<br/>
 * 设置保留小数点后位数，设置floatPoint为需要保留的位数，默认为2，即保留2位小数。如果小于0表示不限制保留的位数，大于0表示限制。
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.def.DigitDef.java] 
 * @ClassName:    [DigitDef]  
 * @Description:  [数字校验实现]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:22:13 PM]  
 * @UpdateRemark: [说明本次修改内容] 
 * @Version:      [v1.0]
 *
 */
public class DigitDef implements ConstraintValidator<Digit, String> {
	private Digit digit;
	
	@Override
	public void initialize(Digit constraintAnnotation) {
		this.digit = constraintAnnotation;
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(StringUtils.isEmpty(value)){
			return true;
		}
		String regex = "[0-9]+";
		if(digit.ableFloat()){
			int point = digit.floatPoint();
			if(point < 0){
				regex += "[\\.]+[0-9]+";
			}else if(point > 0){
				regex += "[\\.]+[0-9]{1,"+point+"}";
			}
		}
		
		if(digit.ableNegative()){
			regex = "-?" + regex;
		}
		regex = "^" + regex + "$";
		Pattern pattern = Pattern.compile(regex); 
		Matcher isNum = pattern.matcher(value);
		if( !isNum.matches() ){
		    return false; 
		}
		int val = Integer.valueOf(value);
		if(val > digit.maxValue()){
			return false;
		}
		if(val < digit.minValue()){
			return false;
		}
		return true;
	}
}
