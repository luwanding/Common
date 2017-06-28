package com.chyjr.platform.common.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * 
 * @ProjectName:  [PlatformCommon]
 * @Package:      [com.chyjr.platform.common.validator.BeanValidator.java] 
 * @ClassName:    [BeanValidator]  
 * @Description:  [Bean 校验器]  
 * @Author:       [weslly]  
 * @CreateDate:   [Oct 14, 2014 5:17:41 PM]  
 * @UpdateUser:   [weslly]  
 * @UpdateDate:   [Oct 14, 2014 5:17:41 PM]  
 * @UpdateRemark: [] 
 * @Version:      [v1.0]
 *
 */
public class BeanValidator {
	
    private static Validator validator = null;
    
    /**
     * 获得验证对象
     */
    public static Validator getValidator(){
    	if (validator == null) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
    	return validator;
    }
    
    /**
     * 按照分组验证Bean是否符合规则。
     * @param t 需要验证的类型。
     * @param groups 分组校验标识，可从<code>com.chyjr.platform.common.validator.groups</code>包中查找或新增你需要的分组。省略则按照所有的规则进行校验。
     * @return
     */
    public static <T> ValidationResult validate(T t, Class<?>... groups) {
    	ValidationResult bvr = new ValidationResult();
    	getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(t, groups);
        if(violations.isEmpty()){
        }else{
        	bvr.setErros(true);
        	for (ConstraintViolation<T> v : violations) {
                bvr.addItem(v.getPropertyPath().toString(), v.getMessage());
            }
        }
        return bvr;
    }
}
