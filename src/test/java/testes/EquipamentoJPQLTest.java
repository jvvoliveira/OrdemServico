package testes;

import entidades.Equipamento;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.Status;

public class EquipamentoJPQLTest extends GenericTest {

    @Test
    public void equipamentosDeUmCliente() {
        String jpql = "SELECT e FROM Equipamento e WHERE e.servico.cliente.id = ?1";
        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, 5L);
        List<Equipamento> equipamentos = query.getResultList();
        assertEquals(1, equipamentos.get(0).getId());
        assertEquals(1, equipamentos.size());
        assertEquals("Motorola", equipamentos.get(0).getMarca());
    }

    @Test
    public void equipamentosDeUmFuncionarioAtendente() {
        String jpql = "SELECT e FROM Equipamento e WHERE e.servico.funcionario.id = ?1";
        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, 1L);
        List<Equipamento> equipamentos = query.getResultList();
        assertEquals(2, equipamentos.get(0).getId());
        assertEquals(4, equipamentos.size());
    }

    @Test
    public void equipamentosDeUmFuncionarioAtendenteFinalizados() {
        String jpql = "SELECT e FROM Equipamento e WHERE e.servico "
                + "IN (SELECT s FROM Servico s WHERE s.funcionario.id = ?1 AND s.status = ?2)";

        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, 2);
        query.setParameter(2, Status.FINALIZADO);
        List<Equipamento> equipamentos = query.getResultList();
        assertEquals("MotoG G4", equipamentos.get(0).getModelo());
        assertEquals(1, equipamentos.size());
    }

    @Test
    public void equipamentoComMaiorMaoDeObra() {
        String jpql = "SELECT MAX(e.maoObra) FROM Equipamento e";

        TypedQuery<Double> query = em.createQuery(jpql, Double.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        double maiorMaoDeObra = query.getSingleResult();

        assertEquals(95.0, maiorMaoDeObra, 0);
        //valor esperado = 95, intervalo entre valores deve ser 0
    }

    @Test
    public void quantidadeDeEquipamentosQueTiveramServicoCancelado() {
        String jpql = "SELECT COUNT(e) FROM Equipamento e where e.servico "
                + "IN (SELECT s FROM Servico s WHERE s.status = ?1)";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter(1, Status.CANCELADO);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        Long qtdEquipamentosCancelados = query.getSingleResult();
        
        assertEquals(1L, qtdEquipamentosCancelados, 0);
        //quantidade esperada = 1, intervalo entre valores deve ser 0
    }
}
