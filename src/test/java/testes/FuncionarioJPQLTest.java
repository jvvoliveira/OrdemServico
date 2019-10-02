package testes;

import entidades.Funcionario;
import entidades.Servico;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;
import utils.Status;

public class FuncionarioJPQLTest extends GenericTest {
    @Test
    public void funcionariosQueMenosAtendeuExecutou(){
        String jpql = "SELECT f FROM  Funcionario f WHERE (SIZE(f.servicos)+SIZE(f.equipamentos)) ="
                + "SELECT MIN(SIZE(fmin.servicos)+SIZE(fmin.equipamentos)) FROM  Funcionario fmin";
        TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        List<Funcionario> func = query.getResultList();
        assertEquals(1L, func.get(0).getId());
        assertEquals(4L, func.get(1).getId());
        assertEquals(2, func.size());
    }
    
    @Test
    public void funcionariosQueAtenderamUmClienteEspecifico(){
        String jpql = "SELECT f FROM Funcionario f, Servico s WHERE s MEMBER OF f.servicos AND s.cliente.id = ?1";
        
        TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, 6L);
        List<Funcionario> func = query.getResultList();
        assertEquals(1L, func.get(0).getId());
        assertEquals(3L, func.get(1).getId());
    }
    
    @Test
    public void funcionariosExecutaramUmServicoCancelado(){
        String jpql = "SELECT e.funcionario FROM Equipamento e WHERE e.servico.status = ?1";
        
        TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        query.setParameter(1, Status.CANCELADO);
        List<Funcionario> func = query.getResultList();
        assertEquals(4L, func.get(0).getId());
    }
    
    @Test
    public void funcionarioQueExecutouEAtendeuUmMesmoServico(){
        String jpql = "SELECT e.funcionario FROM Equipamento e WHERE e.servico.funcionario.id = e.funcionario.id";
        
        TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

        List<Funcionario> func = query.getResultList();
        assertEquals(2L, func.get(0).getId());
        assertEquals(1, func.size());
        
    }
    
    @Test
    public void funcionarioPorMatricula(){
        String jpql = "SELECT f FROM Funcionario f WHERE f.matricula = ?1";
        
        TypedQuery<Funcionario> query = em.createQuery(jpql, Funcionario.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, "201808OS3");

        Funcionario func = query.getSingleResult();
        assertNotNull(func);
        assertEquals(3L, func.getId());
        
        
    }
}
