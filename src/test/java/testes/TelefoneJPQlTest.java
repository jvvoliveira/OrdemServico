package testes;

import entidades.Cliente;
import entidades.Servico;
import entidades.Telefone;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;
import utils.Status;


public class TelefoneJPQlTest extends GenericTest {
    @Test
    public void telefoneDeClientesComServicosFinalizados(){
        String jpql = "SELECT t FROM Telefone t, Servico s WHERE s.status = ?1 AND t.cliente.id = s.cliente.id";

        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, Status.FINALIZADO);

        List<Telefone> telefones = query.getResultList();
        assertEquals(3, telefones.size());
        assertEquals(1L, telefones.get(0).getId());
        assertEquals(2L, telefones.get(1).getId());
        assertEquals(3L, telefones.get(2).getId());
    }
    
    @Test
    public void seTelefoneDeUmClienteComMaisDeUmServico(){
        String jpql = "SELECT t FROM Telefone t WHERE t.cliente IN (SELECT c FROM Cliente c WHERE SIZE(c.servicos) > 1)";
        
        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        List<Telefone> telefones = query.getResultList();
        assertEquals(2, telefones.size());
        assertEquals(4L, telefones.get(0).getId());
        assertEquals(5L, telefones.get(1).getId());
    }
    
    @Test
    public void telefonesSemDDD(){
        
        String jpql = "SELECT t FROM Telefone t WHERE t.ddd IS NULL ";           
        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        List<Telefone> telefones = query.getResultList();
        assertFalse(telefones.size() > 0);
    }
    
    public void telefoneDeUmClienteComEquipamentosDeUmaMarca(){
        String jpql = "SELECT t FROM Telefone t WHERE t.cliente.servicos IN (SELECT e.servico FROM Equipamento e WHERE e.marca LIKE ?1)";
        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, "dell");
        List<Telefone> telefones = query.getResultList();
        assertEquals(2, telefones.size());
        assertEquals(4L, telefones.get(0).getId());
        assertEquals(5L, telefones.get(1).getId());
    }
}
