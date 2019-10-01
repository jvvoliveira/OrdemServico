package testes;

import entidades.Telefone;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TelefoneValidationTest extends GenericTest {

    @Test(expected = ConstraintViolationException.class)
    public void persistTelefoneInvalido() {
        Telefone telefone = new Telefone();

        try {
            System.out.println("--------------ID-----------------");
            System.out.println(telefone.getId());

            telefone.setDdd("34553546");
            telefone.setNumero("1145654677674646561");

            em.persist(telefone);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Telefone.numero: Quantidade incorreta de caracteres para número de telefone"),
                                startsWith("class entidades.Telefone.ddd: Quantidade incorreta de caracteres para DDD"),
                                startsWith("class entidades.Telefone.cliente: Telefone precisa estar associado a um cliente")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            System.out.println("--------------ID-----------------");
            System.out.println(telefone.getId());
            assertEquals(0, telefone.getId());
            throw ex;
        }
    }
    
    @Test(expected = ConstraintViolationException.class)
    public void persistTelefoneComCamposNulos() {
        Telefone telefone = new Telefone();
        try {
            em.persist(telefone);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Telefone.numero: Número de telefone não pode ser nulo"),
                                startsWith("class entidades.Telefone.ddd: DDD não pode ser nulo"),
                                startsWith("class entidades.Telefone.cliente: Telefone precisa estar associado a um cliente")
                        )
                );
            });
            assertEquals(3, constraintViolations.size());
            assertEquals(0, telefone.getId());
            throw ex;
        }
    }
}
