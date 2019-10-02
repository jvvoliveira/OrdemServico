
package testes;

import entidades.Endereco;
import entidades.Funcionario;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class EnderecoValidationTest extends GenericTest{
        
     @Test(expected = ConstraintViolationException.class)     
    public void persistEnderecoInvalido() {
            Endereco endereco = new Endereco();
            String stringMaiorQue150 = null;
        for (int i = 0; i < 152; i++) {
            stringMaiorQue150 += "a";
        }

        try {
            endereco.setCidade(stringMaiorQue150);
            endereco.setRua(stringMaiorQue150);
            endereco.setComplemento(stringMaiorQue150);
            endereco.setBairro(stringMaiorQue150);
            endereco.setCep(stringMaiorQue150);
            
            em.persist(endereco);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Endereco.cidade: Caracteres a mais para cidade"),
                                startsWith("class entidades.Endereco.rua: Caracteres a mais para rua"),
                                startsWith("class entidades.Endereco.complemento: Caracteres a mais para complemento"),
                                startsWith("class entidades.Endereco.bairro: Caracteres a mais para bairro"),
                                startsWith("class entidades.Endereco.cep: Quantidade incorreta de caracteres para CEP")         
                        )
                );
            });
            assertEquals(5, constraintViolations.size());
            assertEquals(0, endereco.getId());
            throw ex;
        }
        }
    
    @Test(expected = ConstraintViolationException.class)
    public void persistEnderecoComTudoNulo() {
        Endereco endereco = new Endereco();

        try {
            em.persist(endereco);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Endereco.cidade: Cidade não pode ser nulo"),
                                startsWith("class entidades.Endereco.rua: Logradouro não pode ser nulo"),
                                startsWith("class entidades.Endereco.cep: CEP não pode ser nulo")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertEquals(0, endereco.getId());
            throw ex;
        }
    }

}
