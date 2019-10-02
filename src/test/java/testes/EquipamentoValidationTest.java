package testes;

import entidades.Equipamento;
import entidades.Servico;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class EquipamentoValidationTest extends GenericTest {
    
    @Test(expected = ConstraintViolationException.class)
    public void persistEquipamentoInvalido() {
        Equipamento equipamento = new Equipamento();
         String stringMaiorQue400 = null;
        for (int i = 0; i < 402; i++) {
            stringMaiorQue400 += "a";
        }
         String stringMaiorQue30 = null;
        for (int i = 0; i < 32; i++) {
            stringMaiorQue30 += "a";
        }
        
        try {
            equipamento.setDescricao(stringMaiorQue400);
            equipamento.setMarca(stringMaiorQue30);
            equipamento.setModelo(stringMaiorQue30);
            equipamento.setSerie(stringMaiorQue30);
            equipamento.setDefeito(stringMaiorQue400);
            equipamento.setSolucao(stringMaiorQue400);
            equipamento.setServico(new Servico());

            em.persist(equipamento);

        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Equipamento.descricao: Caracteres a mais na descrição do equipamento"),
                                startsWith("class entidades.Equipamento.marca: Caracteres a mais na marca do equipamento"),
                                startsWith("class entidades.Equipamento.modelo: Caracteres a mais no modelo do equipamento"),
                                startsWith("class entidades.Equipamento.serie: Caracteres a mais na série do equipamento"),
                                startsWith("class entidades.Equipamento.defeito: Caracteres a mais no defeito do equipamento"),
                                startsWith("class entidades.Equipamento.solucao: Caracteres a mais na solução do equipamento")
                        )
                );
            });
            assertEquals(6, constraintViolations.size());
            assertEquals(0, equipamento.getId());
            throw ex;
        }
    }
    @Test(expected = ConstraintViolationException.class)
    public void persistEquipamentoComTudoNulo() {
        Equipamento equipamento = new Equipamento();
        
        try {
            em.persist(equipamento);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
            constraintViolations.forEach((ConstraintViolation<?> violation) -> {
                assertThat(violation.getRootBeanClass() + "." + violation.getPropertyPath() + ": " + violation.getMessage(),
                        CoreMatchers.anyOf(
                                startsWith("class entidades.Equipamento.descricao: Descrição do equipamento não pode ser nulo"),
                                startsWith("class entidades.Equipamento.marca: Marca do equipamento não pode ser nulo"),
                                startsWith("class entidades.Equipamento.modelo: Modelo do equipamento não pode ser nulo"),
                                startsWith("class entidades.Equipamento.serie: Série do equipamento não pode ser nulo"),
                                startsWith("class entidades.Equipamento.defeito: Defeito do equipamento não pode ser nulo"),
                                startsWith("class entidades.Equipamento.servico: Equipamento deve estar associado a um serviço")
                        )
                );
            });
            assertEquals(6, constraintViolations.size());
            assertEquals(0, equipamento.getId());
            throw ex;
        }
    }
    
}
