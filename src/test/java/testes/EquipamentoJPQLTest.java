
package testes;

import entidades.Equipamento;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import org.junit.Test;
import static org.junit.Assert.*;


public class EquipamentoJPQLTest extends GenericTest{
    
    @Test
    public void equipamentosDeUmCliente(){
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
    public void equipamentosDeUmFuncionarioAtendente(){
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
    public void equipamentosDeUmFuncionarioAtendenteFinalizados(){
        String jpql = "SELECT s.equipamentos FROM Servico s WHERE s.status LIKE ?1 "
                + "IN (SELECT s FROM Servico s WHERE s.funcionario.id = ?2)";
//        SELECT i FROM Item i WHERE i.vendedor IN (SELECT v FROM Vendedor v WHERE v.reputacao = :reputacao)
        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, "FINALIZADO");
        query.setParameter(2, 2);
        List<Equipamento> equipamentos = query.getResultList();
        assertEquals("MotoG G4", equipamentos.get(0).getModelo());
        assertEquals(1, equipamentos.size());
    }
}
