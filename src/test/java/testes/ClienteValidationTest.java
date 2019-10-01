package testes;

import entidades.Cliente;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class ClienteValidationTest extends GenericTest {

    @Test(expected = ConstraintViolationException.class)
    public void persistClienteInvalido() {
        Cliente cliente = new Cliente();
        String stringMaiorQue100 = null;
        for (int i = 0; i < 102; i++) {
            stringMaiorQue100 += "a";
        }

        try {
            cliente.setNome(stringMaiorQue100);
            cliente.setEmail(stringMaiorQue100);
            cliente.setDataNasc(getData(28, 10, 2022));
            cliente.setCpf("111.321.321-3516554657564564564565475");

            em.persist(cliente);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Cliente.nome: Caracteres a mais para nome"),
                                startsWith("class entidades.Cliente.email: Email inválido"),
                                startsWith("class entidades.Cliente.email: Caracteres a mais para email"),
                                startsWith("class entidades.Cliente.dataNasc: Data de Nascimento inválida"),
                                startsWith("class entidades.Cliente.cpf: CPF com quantidade incorreta de caracteres"),
                                startsWith("class entidades.Cliente.cpf: Erro de validação do CPF(xxx.xxx.xxx-xx) / CNPJ (xx.xxx.xxx/xxxx-xx)")
                        )
                );
            });
            assertEquals(6, constraintViolations.size());
            assertEquals(0, cliente.getId());
            throw ex;
        }
    }

    @Test(expected = ConstraintViolationException.class)
    public void persistClienteComTudoNulo() {
        Cliente cliente = new Cliente();

        try {
            em.persist(cliente);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Cliente.nome: Nome não pode ser nulo"),
                                startsWith("class entidades.Cliente.email: Email não pode ser nulo"),
                                startsWith("class entidades.Cliente.dataNasc: Data de Nascimento não pode ser nulo")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertEquals(0, cliente.getId());
            throw ex;
        }
    }

}
