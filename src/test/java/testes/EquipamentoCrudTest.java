
package testes;

import entidades.Cliente;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Servico;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import utils.Status;


public class EquipamentoCrudTest extends GenericTest{
    @Test
    public void persistirEquipamento() {
        logger.info("Executando persistirEquipamento()");
        Equipamento equipamento = criarEquipamento("note 7", "toques na tela não funcionam", "redmi", "25ght6p", "celular com capinha transparente");
        Funcionario func1 = criarFuncionario("funcNome", "Técnico", "func123@teste.com", "852369OS4");
        Cliente cliente = criarCliente("joão Teste", "joaojoao@email.com", "123.321.654-45");
        Servico servico = criarServico(Status.ABERTO);
        servico.setCliente(cliente);
        servico.setInicio(getData(20, 9, 2019));
        servico.setPrevFim(getData(30, 11, 2019));
        servico.setFuncionario(func1);
        
        equipamento.setServico(servico);
        equipamento.setFuncionario(func1);
        em.persist(equipamento);
        em.flush();
        
        assertNotNull(equipamento.getId());
        assertNotNull(equipamento.getServico().getId());
        assertNotNull(equipamento.getFuncionario().getId());
        
    }

    @Test
    public void atualizarEquipamento() {
        logger.info("Executando atualizarEquipamento()");
        
        Long id = 2L; //saber ID exato do cliente
        Equipamento equipamento = em.find(Equipamento.class, id);
        String solucaoNova = "altaração de solução";
        equipamento.setSolucao(solucaoNova);
        equipamento.setMaoObra(100.25);
        em.flush();
        
        String jpql = "SELECT e FROM Equipamento e WHERE e.id = ?1";
        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, id);
        equipamento = query.getSingleResult();
        
        assertEquals(equipamento.getSolucao(), solucaoNova);
        assertEquals(equipamento.getMaoObra(), 100.25, 0);
    }

    @Test
    public void atualizarEquipamentoMerge() {
        logger.info("Executando atualizarEquipamentoMerge()");
        
        Long id = 3L; //saber ID exato do cliente
        Equipamento equipamento = em.find(Equipamento.class, id);
        String novoDefeito = "Celular inteiro quebrado, caiu da janela";
        equipamento.setDefeito(novoDefeito);
        
        em.clear();
        em.merge(equipamento);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        equipamento = em.find(Equipamento.class, id, properties);
        assertEquals(novoDefeito, equipamento.getDefeito());
    }

    @Test
    public void removerEquipamento() {
        logger.info("Executando removerEquipamento()");
        Equipamento equipamento = em.find(Equipamento.class, 1L);
        assertNotNull(equipamento);
        em.remove(equipamento);
        
        Equipamento checkEquipamento = em.find(Equipamento.class, 1L);
        assertNull(checkEquipamento);
    }
}
