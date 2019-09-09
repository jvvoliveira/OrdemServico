package testes;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Pessoa;
import entidades.Servico;
import entidades.Telefone;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import utils.Status;

public class ClienteCrudTest extends GenericTest {

    @Test
    public void persistirCliente() {
        logger.info("Executando persistirCliente()");
        Cliente cliente = criarCliente("Teste", "testeteste.@gmail.com" ,"123467988");
        em.persist(cliente);
        em.flush();

        assertNotNull(cliente.getId());
        assertNotNull(cliente.getEndereco().getId());
        assertNotNull(cliente.getTelefones().get(0).getId());
        assertNotNull(cliente.getServicos().get(0).getId());
    }

    @Test
    public void atualizarCliente() {
        logger.info("Executando atualizarCliente()");

        String novoEmail = "fulano_de_tal@gmail.com";
        Telefone telefone = new Telefone();
        telefone.setDdd(81);
        telefone.setNumero("(81) 999999999");
        Long id = 1L; //saber ID exato do cliente
        logger.info("Pegando do banco ------------------------------");
        Cliente cliente = em.find(Cliente.class, id);
        cliente.setEmail(novoEmail);
        cliente.setTelefones(telefone);
        logger.info("mandando pro banco ------------------------------");
        em.flush();
        logger.info("Preparando consulta ------------------------------");
        String jpql = "SELECT c FROM Pessoa c WHERE c.id = ?1";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        //obrigatoriamente ir para o banco
        logger.info("----------------------1");
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, id);
        logger.info("-----------------------2");
        cliente = query.getSingleResult();
        logger.info("--------------------------3");

        assertEquals(novoEmail, cliente.getEmail());
        assertEquals(cliente.getTelefones().get(0).getNumero(), telefone.getNumero());
    }

    @Test
    public void atualizarClienteMerge() {
        logger.info("Executando atualizarClienteMerge()");
        String novoEmail = "fulano_de_tal2@gmail.com";
        Telefone telefone = new Telefone();
        telefone.setDdd(81);
        telefone.setNumero("(81) 888888888");
        Long id = 1L;
        Cliente cliente = em.find(Cliente.class, id);
        cliente.setEmail(novoEmail);
        cliente.setTelefones(telefone);

        em.clear();
        em.merge(cliente);

        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        cliente = em.find(Cliente.class, id, properties);
        assertEquals(novoEmail, cliente.getEmail());
        assertEquals(cliente.getTelefones().get(0).getNumero(), telefone.getNumero());
    }

    @Test
    public void removerCliente() {
        logger.info("Executando removerCliente()");
        Cliente cliente = em.find(Cliente.class, 1L);
        em.remove(cliente);
        Pessoa pessoa = em.find(Pessoa.class, 1L);
        assertNull(pessoa);
    }
}
