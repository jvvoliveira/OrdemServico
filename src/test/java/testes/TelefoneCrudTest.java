
package testes;

import entidades.Cliente;
import entidades.Telefone;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;


public class TelefoneCrudTest extends GenericTest{
    
    @Test
    public void persistirTelefone() {
        logger.info("Executando persistirTelefone()");
        Cliente cliente = criarCliente("teste João", "teste1.2@teste.com", "987.654.321-31");
        Telefone telefone = preencherTelefone("32683268", cliente);
        cliente.addTelefones(telefone);
       
        em.persist(telefone);
        em.flush();
        
        assertNotNull(telefone.getId());
        assertNotNull(telefone.getCliente().getId());
        
    }

    @Test
    public void atualizarTelefone() {
        logger.info("Executando atualizarTelefone()");
        
        Long id = 2L; //saber ID exato do cliente
        Telefone telefone = em.find(Telefone.class, id);
        String ddd = "11";
        telefone.setDdd(ddd);
        em.flush();
        
        String jpql = "SELECT t FROM Telefone t WHERE t.id = ?1";
        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, id);
        telefone = query.getSingleResult();
        
        assertEquals(telefone.getDdd(), ddd);
    }

    @Test
    public void atualizarTelefoneMerge() {
        logger.info("Executando atualizarTelefoneMerge()");
        
        Long id = 3L; //saber ID exato do cliente
        Telefone telefone = em.find(Telefone.class, id);
        String ddd = "21";
        telefone.setDdd(ddd);
        
        em.clear();
        em.merge(telefone);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        telefone = em.find(Telefone.class, id, properties);
        assertEquals(telefone.getDdd(), ddd);
    }

    @Test
    public void removerTelefone() {
        logger.info("Executando removerTelefone()");
        Telefone telefone = em.find(Telefone.class, 6L);
        assertNotNull(telefone);
        em.remove(telefone);
        
        Telefone checkTelefone = em.find(Telefone.class, 6L);
        assertNull(checkTelefone);
    }
}
