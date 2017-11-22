package cn.tech.yozo.factoryrp.vo.validation.validator;

import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.vo.validation.NotEmpty;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 验证器
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty,String>{


    @Override
    public void initialize(NotEmpty notEmpty) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !CheckParam.isNull(value);
    }

}
