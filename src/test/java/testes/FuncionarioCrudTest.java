package testes;

import entidades.Equipamento;
import entidades.Funcionario;
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


public class FuncionarioCrudTest extends GenericTest{
    @Test
    public void persistirFuncionario() {
        logger.info("Executando persistirFuncionario()");
        
        Funcionario funcionario = criarFuncionario("João Funcionario", "Técnico", "jfunc@tecnico.com", "111111");
        Equipamento equipamento = criarEquipamento("xcv123", "não liga", "samsung", "32fg1654", "celular com capinha");
        Equipamento equipamento2 = criarEquipamento("asd321", "não liga", "motorola", "ftg765", "celular");
        
        funcionario.setEquipamentos(equipamento);
        funcionario.setEquipamentos(equipamento2);
        
        em.persist(funcionario);
        em.flush();
        
        assertNotNull(funcionario.getId());
        assertNotNull(funcionario.getEquipamentos().get(0).getId());
        assertNotNull(funcionario.getEquipamentos().get(1).getId());
    }

    @Test
    public void atualizarFuncionario() {
        logger.info("Executando atualizarFuncionario()");
        
        Long id = 2L; //saber ID exato do cliente
        Funcionario funcionario = em.find(Funcionario.class, id);
        String novoNome = "João Victor Vasconcelos";
        String novoEmail = "jvv@gmail.com";
        funcionario.setNome(novoNome);
        funcionario.setEmail(novoEmail);
        em.flush();
        
        String jpql = "SELECT f FROM Pessoa f WHERE f.id = ?1";
        TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, id);
        funcionario = query.getSingleResult();
        
        assertEquals(funcionario.getNome(), novoNome);
        assertEquals(funcionario.getEmail(), novoEmail);
    }

    @Test
    public void atualizarFuncionarioMerge() {
        logger.info("Executando atualizarFuncionarioMerge()");
        
        Long id = 3L; //saber ID exato do cliente
        
        Funcionario funcionario = em.find(Funcionario.class, id);
        String novoNome = "João Paulo Teste";
        String novoEmail = "jpTeste@gmail.com";
        funcionario.setNome(novoNome);
        funcionario.setEmail(novoEmail);
        
        em.clear();
        em.merge(funcionario);
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        funcionario = em.find(Funcionario.class, id, properties);
        assertEquals(funcionario.getNome(), novoNome);
        assertEquals(funcionario.getEmail(), novoEmail);
    }

    @Test
    public void removerFuncionario() {
        logger.info("Executando removerFuncionario()");
        
        Funcionario funcionario = em.find(Funcionario.class, 1L);
        assertNotNull(funcionario);
        
        //retirar fk dos equipamentos desse funcionário
        String jpql = "SELECT e FROM Equipamento e WHERE e.funcionario.id = ?1";
        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, 1L);
        List<Equipamento> equipamentos = query.getResultList();
        equipamentos.get(0).setFuncionario(null);
        equipamentos.get(1).setFuncionario(null);
        em.flush();
                
        em.remove(funcionario);
        
        ((JpaCache)emf.getCache()).clear();
        Funcionario checkFuncionario = em.find(Funcionario.class, 1L);
        assertNull(checkFuncionario);
    }
}
