package validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CPF_CNPJ_Validator.class)
@Documented

public @interface ValidaCPF_CNPJ {

    String message() default "Erro de validação do CPF(xxx.xxx.xxx-xx) / CNPJ (xx.xxx.xxx/xxxx-xx)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
