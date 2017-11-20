package cn.tech.yozo.factoryrp.vo.validation;

import cn.tech.yozo.factoryrp.vo.validation.validator.IsNumberStrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 非空注解
 */
@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsNumberStrValidator.class)
public @interface IsNumberStr {



    String message() default "必须为数字";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
