/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import entidades.Endereco;
import entidades.Equipamento;
import entidades.Servico;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;
import utils.Status;

/**
 *
 * @author beatrizlima
 */
public class EnderecoJPQLTest extends GenericTest {
    
    @Test
    public void enderecoClienteComMaisDeUmServicos(){
        String jpql = "SELECT c.endereco FROM Cliente c WHERE SIZE(c.servicos) > 1";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
       
        List<Endereco> endereco = query.getResultList();
        assertEquals(2L, endereco.get(0).getId());
        
    }
    
    @Test
    public void enderecoClienteCPF(){
        String jpql ="SELECT e FROM Endereco e WHERE e.cliente.cpf = ?1";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
         query.setParameter(1, "327.392.580-97");
         
        Endereco endereco = query.getSingleResult();
        assertEquals(4L, endereco.getId());

    }
    
    @Test
    public void enderecoPorMoledo(){
        String jpql ="SELECT e.servico.cliente.endereco FROM Equipamento e WHERE e.modelo = ?1";
         TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        query.setParameter(1, "MotoG G4");
         
        Endereco endereco = query.getSingleResult();
        assertEquals(1L, endereco.getId());
    }
    
    @Test
    public void enderecoPorCliente(){
        String jpql = "SELECT e FROM Endereco e WHERE e.cliente.nome LIKE ?1";
        TypedQuery<Endereco> query = em.createQuery(jpql, Endereco.class);
        
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        query.setParameter(1, "Arlindo Cavalcanti Filho");
         
        Endereco endereco = query.getSingleResult();
        assertEquals(2L, endereco.getId());
    }
    
    
}
