package testes;

import entidades.Cliente;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ClienteJPQLTest extends GenericTest {

    @Test
    public void clientePorNomeEspecifico() {
        String jpql = "SELECT c FROM Cliente c WHERE c.nome LIKE ?1";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, "Amanda Costa e Silva");

        Cliente cliente = query.getSingleResult();

        assertEquals(5L, cliente.getId());
    }

    @Test
    public void clientePorTelefoneEspecifico() {
        String jpql = "SELECT c FROM Cliente c WHERE EXISTS "
                + "(SELECT t FROM Telefone t WHERE t.cliente = c AND t.numero = ?1)";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, "987600987");

        Cliente cliente = query.getSingleResult();

        assertEquals(6L, cliente.getId());
    }

    @Test
    public void clientesQueNasceramEntre1980_1990() {
        String jpql = "SELECT c FROM Cliente c WHERE c.dataNasc BETWEEN ?1 AND ?2 ";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, getData(1, Calendar.JANUARY, 1980));
        query.setParameter(2, getData(1, Calendar.JANUARY, 1990));

        List<Cliente> clientes = query.getResultList();

        assertEquals(2, clientes.size());
        assertEquals(Cliente.class, clientes.get(0).getClass());
        assertEquals(Cliente.class, clientes.get(1).getClass());
    }

    @Test
    public void clientesOrdenadosPorNomeComApenasUmServico() {
        String jpql = "SELECT c FROM Cliente c WHERE SIZE(c.servicos) = 1 ORDER BY c.nome ";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        List<Cliente> clientes = query.getResultList();

        assertEquals(3, clientes.size());
        assertEquals("Amanda Costa e Silva", clientes.get(0).getNome());
        assertEquals("Boa Vista Paraiso das Frutas ME", clientes.get(1).getNome());
        assertEquals("Bruna Amancio Vilanova", clientes.get(2).getNome());           
    }

    @Test
    public void todosOsClientesPeloEnderecoNotNull() {
        String jpql = "SELECT c FROM Cliente c WHERE c.endereco IS NOT NULL ";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        List<Cliente> clientes = query.getResultList();

        assertEquals(4, clientes.size());
        long id = 5L;
        for (int i = 0; i < clientes.size(); i++) {
            assertEquals(id, clientes.get(i).getId());
            id++;
        }
    }

    @Test
    public void clienteMaisNovo() {
        String jpql = "SELECT c FROM Cliente c WHERE c.dataNasc >= ALL (SELECT c2.dataNasc FROM Cliente c2)";

        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        Cliente cliente = query.getSingleResult();

        assertEquals("Bruna Amancio Vilanova", cliente.getNome());
    }
}
