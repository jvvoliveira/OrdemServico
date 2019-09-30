package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Matricula_Validator implements ConstraintValidator<Valida_Matricula, String>{

    @Override
    public void initialize(Valida_Matricula a) {
       
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        if(t == null){
            return true;
        }
        Boolean resp = t.matches("^((\\d{6})OS(\\d{1}))*$"); //CPF
        //* pode aparecer 0, 1 ou várias vezes;
        //^ casa o começo da cadeira de caracteres
        //$ casa o começo da cadeira de caracteres
        return resp;
    }
}
