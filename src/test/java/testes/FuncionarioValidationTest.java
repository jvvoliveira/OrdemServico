package testes;

import entidades.Funcionario;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;


public class FuncionarioValidationTest extends GenericTest{
    
    @Test(expected = ConstraintViolationException.class)
    public void persistFuncionarioInvalido() {
        Funcionario funcionario = new Funcionario();
        String stringMaiorQue100 = null;
        for (int i = 0; i < 102; i++) {
            stringMaiorQue100 += "a";
        }

        try {
            funcionario.setNome(stringMaiorQue100);
            funcionario.setEmail(stringMaiorQue100);
            funcionario.setDataNasc(getData(28, 10, 2022));
            funcionario.setMatricula("213423456364647657467");
            funcionario.setCargo(stringMaiorQue100);

            em.persist(funcionario);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Funcionario.nome: Caracteres a mais para nome"),
                                startsWith("class entidades.Funcionario.email: Email inválido"),
                                startsWith("class entidades.Funcionario.email: Caracteres a mais para email"),
                                startsWith("class entidades.Funcionario.dataNasc: Data de Nascimento inválida"),
                                startsWith("class entidades.Funcionario.matricula: Matrícula com quantidade incorreta de caracteres"),
                                startsWith("class entidades.Funcionario.matricula: Matrícula inválida"),
                                startsWith("class entidades.Funcionario.cargo: Caracteres a mais para cargo")      
                        )
                );
            });
            assertEquals(7, constraintViolations.size());
            assertEquals(0, funcionario.getId());
            throw ex;
        }
    }
    
     @Test(expected = ConstraintViolationException.class)
    public void persistFuncionarioComTudoNulo() {
        Funcionario funcionario = new Funcionario();

        try {
            em.persist(funcionario);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Funcionario.nome: Nome não pode ser nulo"),
                                startsWith("class entidades.Funcionario.email: Email não pode ser nulo"),
                                startsWith("class entidades.Funcionario.dataNasc: Data de Nascimento não pode ser nulo")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertEquals(0, funcionario.getId());
            throw ex;
        }
    }
}
