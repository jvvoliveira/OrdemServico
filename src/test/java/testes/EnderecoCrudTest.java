package testes;

import entidades.Cliente;
import entidades.Endereco;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

public class EnderecoCrudTest extends GenericTest {

    @Test
    public void persistirEndereco() {
        logger.info("Executando persistirEndereco()");
        Cliente cliente = criarCliente("teste Gideone", "gida17@teste.com", "987.884.323-31");
        Endereco endereco = criarEndereco("Centro", "São Lourenço da Mata", "54730-410", "Rua los Bolados", 17, "Casa");

        cliente.setEndereco(endereco);
//        try {
            em.persist(endereco);
            em.flush();

            assertNotNull(endereco.getId());
            assertNotNull(endereco.getCliente().getId());
//        } catch (ConstraintViolationException e) {
//            System.out.println("------------ERRO---------");
//            System.out.println(e.getConstraintViolations().toString());
//        }
    }

    @Test
    public void atualizarEndereco() {
        logger.info("Executando atualizarEndereco()");

        Long id = 2L; //saber ID exato do cliente
        Endereco endereco = em.find(Endereco.class, id);
        String bairro = "Pixete";
        endereco.setBairro(bairro);
        em.flush();

        String jpql = "SELECT t FROM Endereco t WHERE t.id = ?1";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        endereco = query.getSingleResult();

        assertEquals(endereco.getBairro(), bairro);
    }

    @Test
    public void atualizarEnderecoMerge() {
        logger.info("Executando atualizarEnderecoMerge()");

        Long id = 3L; //saber ID exato do cliente
        Endereco endereco = em.find(Endereco.class, id);
        int numero = 23;
        endereco.setNumero(numero);

        em.clear();
        em.merge(endereco);

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        endereco = em.find(Endereco.class, id, properties);
        assertEquals(endereco.getNumero(), numero);
    }

    @Test
    public void removerEndereco() {
        logger.info("Executando removerEndereco()");
        Endereco endereco = em.find(Endereco.class, 1L);
        assertNotNull(endereco);

        //retirar fk os endereços dos clientes
        String jpql = "SELECT c FROM Cliente c WHERE c.endereco.id = ?1";
        TypedQuery<Cliente> queryCLiente = em.createQuery(jpql, Cliente.class);
        //obrigatoriamente ir para o banco
        queryCLiente.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        queryCLiente.setParameter(1, 1L);
        Cliente cliente = queryCLiente.getSingleResult();
        cliente.setEndereco(null);
        endereco.setCliente(null);

        em.remove(endereco);

        Endereco checkEndereco = em.find(Endereco.class, 1L);
        assertNull(checkEndereco);
    }
}
