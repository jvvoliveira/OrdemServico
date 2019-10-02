package testes;

import entidades.Servico;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import utils.Status;

public class ServicoValidationTest extends GenericTest {

    @Test(expected = ConstraintViolationException.class)
    public void persistServicoComCamposNulos() {
        Servico servico = new Servico();

        try {

            em.persist(servico);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Servico.status: Status do serviço não pode ser nulo"),
                                startsWith("class entidades.Servico.inicio: Data de início do serviço não pode ser nulo"),
                                startsWith("class entidades.Servico.prevFim: Data de previsão de fim do serviço não pode ser nulo"),
                                startsWith("class entidades.Servico.cliente: Serviço deve estar associado a um cliente"),
                                startsWith("class entidades.Servico.funcionario: Serviço deve estar associado a um funcionário atendente")
                        )
                );
            });
            assertEquals(5, constraintViolations.size());
            assertEquals(0, servico.getId());
            throw ex;
        }
    }
}
