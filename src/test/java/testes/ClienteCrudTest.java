package testes;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Servico;
import entidades.Telefone;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.jpa.JpaCache;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static testes.GenericTest.emf;

public class ClienteCrudTest extends GenericTest{
    
    @Test
    public void persistirCliente(){
        logger.info("Executando persistirCliente()");
        Cliente cliente = criarCliente("Ana Maria Silva", "anasilva@mail.com", "78767820387");
        Telefone telefone = preencherTelefone("987650987", cliente);
        Endereco endereco = criarEndereco("Peixinhos", "Olinda", "546778459", "Rua do Alvoroço", 76, "Apt");
        //Servico servico = criarServico(Status.ABERTO);
        
        //cliente.setServicos(servico);
        cliente.setEndereco(endereco);
        cliente.setTelefones(telefone);
        //endereco.setCliente(cliente);
        //telefone.setCliente(cliente);
        
        em.persist(cliente);
        em.flush();
        
        assertNotNull(cliente.getId());
        assertNotNull(cliente.getEndereco().getId());
        assertNotNull(cliente.getTelefones().get(0).getId());
    }
    
    @Test
    public void atualizarCliente(){
        logger.info("Executando atualizarCliente()");
        
        Long id = 5L;
        Cliente cliente = em.find(Cliente.class, id);
        String novoNome = "Mariana Silva";
        String novoEmail = "marisilva@mail.com";
        cliente.setNome(novoNome);
        cliente.setEmail(novoEmail);
        em.flush();
        
        String jpql = "SELECT c FROM Pessoa c WHERE c.id = ?1";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, id);
        cliente = query.getSingleResult();
        
        assertEquals(cliente.getNome(), novoNome);
        assertEquals(cliente.getEmail(), novoEmail);
        
    }
    
    @Test
    public void atualizarClienteMerge(){
        logger.info("Executando atualizarClienteMerge()");
        
        Long id = 5L; //saber ID exato do cliente
        
        Cliente cliente = em.find(Cliente.class, id);
        String novoNome = "Mari Ana Silva";
        String novoEmail = "maria.silva@mail.com";
        cliente.setNome(novoNome);
        cliente.setEmail(novoEmail);
        
        
        em.clear();
        em.merge(cliente);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        cliente = em.find(Cliente.class, id, properties);
        assertEquals(cliente.getNome(), novoNome);
        assertEquals(cliente.getEmail(), novoEmail);
    }
    
    @Test 
    public void removerCliente(){
        logger.info("Executando removerCliente()");
        
        Cliente cliente = em.find(Cliente.class, 8L);
        assertNotNull(cliente);
        
        /*
        //retirar fk dos telefones desse cliente
        String jpql = "SELECT t FROM Telefone t WHERE t.cliente.id = ?8";
        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, 8L);
        List<Telefone> telefone = query.getResultList();
        telefone.get(0).setCliente(null);
        em.flush();
        
        em.remove(cliente);
        
        ((JpaCache)emf.getCache()).clear();
        Cliente checkCliente = em.find(Cliente.class, 9L);
        assertNull(checkCliente);*/
        
        //removendo telefones - eles são apagados pois a coluna de fk não pode ter valores nulos
        String jpql = "SELECT t FROM Telefone t";
        TypedQuery<Telefone> query = em.createQuery(jpql, Telefone.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        //query.setParameter(1, 8L);
        List<Telefone> telefones = query.getResultList();
        for(Telefone tel : telefones){
            if(tel.getCliente().getId() == cliente.getId()){
               System.out.println("REMOVENDO: "+tel.getCliente().getNome()+" - "+tel.getCliente().getId()+" E TELEFONE: "+tel.getNumero());
               em.remove(tel);
            }
        }
        
        //remover de cliente de servico - o CLiente id(8) tem servico de id(5)
        Servico servico = em.find(Servico.class, 5L);
        assertNotNull(servico);
        Funcionario funcionario = em.find(Funcionario.class, servico.getFuncionario().getId());
        Equipamento equipamento = em.find(Equipamento.class, servico.getEquipamentos().get(0).getId());
        cliente.setServicos(null);
        funcionario.setServicos(null);
        equipamento.setServico(null);
        //Apagar servico porque fk_cli não pode fica nula
        em.remove(servico);
        
        em.remove(cliente);
        
        ((JpaCache)emf.getCache()).clear();
        Cliente checkCliente = em.find(Cliente.class, 8L);
        assertNull(checkCliente);
    }
    
}
