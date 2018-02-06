package tech.yozo.factoryrp.vo.validation;

import tech.yozo.factoryrp.vo.validation.validator.IsDateStrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * 日期字符串验证注解
 */
@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsDateStrValidator.class)
public @interface IsDateStr {

    String message() default "必须为日期格式的字符串";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
