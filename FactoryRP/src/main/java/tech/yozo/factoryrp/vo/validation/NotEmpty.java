package tech.yozo.factoryrp.vo.validation;

import tech.yozo.factoryrp.vo.validation.validator.NotEmptyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 日期字符串验证注解
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 日期字符串验证注解
 */
@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyValidator.class)
public @interface NotEmpty {



    String message() default "not_null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
