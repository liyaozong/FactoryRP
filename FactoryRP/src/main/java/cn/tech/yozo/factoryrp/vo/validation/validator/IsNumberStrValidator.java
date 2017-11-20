package cn.tech.yozo.factoryrp.vo.validation.validator;

import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.vo.validation.IsNumberStr;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/20
 * @description
 */
public class IsNumberStrValidator implements ConstraintValidator<IsNumberStr,String> {


    @Override
    public void initialize(IsNumberStr isNumberStr) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return CheckParam.isNum(value);
    }
}
