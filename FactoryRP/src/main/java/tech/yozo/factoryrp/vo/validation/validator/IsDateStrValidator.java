package tech.yozo.factoryrp.vo.validation.validator;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.validation.IsDateStr;
import tech.yozo.factoryrp.vo.validation.IsNumberStr;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/20
 * @description
 */
public class IsDateStrValidator implements ConstraintValidator<IsDateStr,String> {


    @Override
    public void initialize(IsDateStr isDateStr) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return CheckParam.isDatetime(value);
    }
}
