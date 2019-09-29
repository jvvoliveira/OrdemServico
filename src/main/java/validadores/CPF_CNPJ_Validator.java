package validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPF_CNPJ_Validator implements ConstraintValidator<ValidaCPF_CNPJ, String>{
    @Override
    public void initialize(ValidaCPF_CNPJ a) {
        
    }

    @Override
    public boolean isValid(String t, ConstraintValidatorContext cvc) {
        Boolean resp = t.matches("^((\\d{3}).(\\d{3}).(\\d{3})-(\\d{2}))*$"); //CPF
        if(resp == false){
            resp = t.matches("^((\\d{2}).(\\d{3}).(\\d{3})/(\\d{4})-(\\d{2}))*$"); //CNPJ
        }
        //* pode aparecer 0, 1 ou várias vezes;
        //^ casa o começo da cadeira de caracteres
        //$ casa o começo da cadeira de caracteres
        System.out.println("-----------------------------resp--------"+resp);
        return resp;
    }
}
