package testes;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Servico;
import entidades.Telefone;
import java.util.ArrayList;
import java.util.Calendar;
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
import static testes.GenericTest.logger;
import utils.Status;


public class ServicoCrudTest extends GenericTest{
    @Test
    public void persistirServico() {
        logger.info("Executando persistirServico()");
        Servico servico = criarServico(Status.ANDAMENTO);
        Funcionario funcionario = criarFuncionario("funcBeltrano", "Atendente", "teste123@gmail.com", "654321");
        Cliente cliente = criarCliente("Cicrano", "cicrano.21@teste.com", "999666325-56");
        Equipamento equipamento = criarEquipamento("note 7", "toques na tela não funcionam", "redmi", "25ght6p", "celular com capinha transparente");
        
        servico.setFuncionario(funcionario);
        servico.setCliente(cliente);
        servico.setEquipamentos(equipamento);
        
        em.persist(servico);
        em.flush();
        
        assertNotNull(servico.getId());
        assertNotNull(servico.getEquipamentos().get(0).getId());
        assertNotNull(servico.getCliente().getId());
        assertNotNull(servico.getFuncionario().getId());
    }

    @Test
    public void atualizarServico() {
        logger.info("Executando atualizarServico()");
        
        Long id = 2L; //saber ID exato do cliente
        Servico servico = em.find(Servico.class, id);
        servico.setStatus(Status.ANDAMENTO);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c.set(Calendar.DAY_OF_MONTH, 22);
        servico.setPrevFim(c.getTime());
        em.flush();
        
        String jpql = "SELECT s FROM Servico s WHERE s.id = ?1";
        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, id);
        servico = query.getSingleResult();
        
        assertEquals(c.getTime(), servico.getPrevFim());
        assertEquals(Status.ANDAMENTO, servico.getStatus());
    }

    @Test
    public void atualizarServicoMerge() {
        logger.info("Executando atualizarServicoMerge()");
        
        Long id = 3L; //saber ID exato do cliente
        Servico servico = em.find(Servico.class, id);
        servico.setStatus(Status.CANCELADO);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c.set(Calendar.DAY_OF_MONTH, 25);
        servico.setPrevFim(c.getTime());
        
        em.clear();
        em.merge(servico);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        servico = em.find(Servico.class, id, properties);
        assertEquals(c.getTime(), servico.getPrevFim());
        assertEquals(Status.CANCELADO, servico.getStatus());
    }

    @Test
    public void removerServico() {
        logger.info("Executando removerServico()");
        Servico servico = em.find(Servico.class, 1L);
        assertNotNull(servico);
        
        Cliente cliente = em.find(Cliente.class, servico.getCliente().getId());
        Funcionario funcionario = em.find(Funcionario.class, servico.getFuncionario().getId());
        Equipamento equipamento = em.find(Equipamento.class, servico.getEquipamentos().get(0).getId());
        
        cliente.setServicos(null);
        funcionario.setServicos(null);
        equipamento.setServico(null);
        em.flush();
        
        em.remove(servico);
        ((JpaCache)emf.getCache()).clear();
        
        Servico checkServico = em.find(Servico.class, 1L);
        assertNull(checkServico);
    }
}
