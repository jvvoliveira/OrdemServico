/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import entidades.Equipamento;
import entidades.Servico;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import static org.junit.Assert.*;
import org.junit.Test;
import utils.Status;

public class ServicoJPQLTest extends GenericTest{
    @Test
    public void valoresDosServicos(){
        String jpql = "SELECT SUM(e.maoObra) + SUM(e.custoPecas) FROM Servico s, Equipamento e WHERE e MEMBER OF s.equipamentos GROUP BY s ";           
        TypedQuery<Double> query = em.createQuery(jpql, Double.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        List<Double> valores = query.getResultList();
        assertEquals(325.88, valores.get(0), 0);
        assertEquals(95.0, valores.get(1), 0);
        assertEquals(48.67, valores.get(2), 0);
        assertEquals(0.0, valores.get(3), 0);
        assertEquals(0.0, valores.get(4), 0);
    }
    
    @Test
    public void servicosAtrasados(){
        String jpql = "SELECT s FROM Servico s WHERE (s.status = ?1 OR s.status = ?2) AND CURRENT_DATE > s.prevFim";

        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);

        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, Status.ANDAMENTO);
        query.setParameter(2, Status.ABERTO);
        List<Servico> servicos = query.getResultList();
        assertEquals(3L, servicos.get(0).getId());
        assertEquals(4L, servicos.get(1).getId());
    }
    
    @Test
    public void servicosAbertosEmUmDeterminadoMes(){
        String jpql = "SELECT s FROM Servico s WHERE s.inicio BETWEEN ?1 AND ?2";

        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
        
        Calendar d1 = Calendar.getInstance();
        d1.set(Calendar.YEAR, 2019);
        d1.set(Calendar.MONTH, 8);
        d1.set(Calendar.DAY_OF_MONTH, 1);
        
        Calendar d30 = Calendar.getInstance();
        d30.set(Calendar.YEAR, 2019);
        d30.set(Calendar.MONTH, 8);
        d30.set(Calendar.DAY_OF_MONTH, 30);
        
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, d1.getTime());
        query.setParameter(2, d30.getTime());
        List<Servico> servicos = query.getResultList();
        assertEquals(4L, servicos.get(0).getId());
        assertEquals(5L, servicos.get(1).getId());
    }
    
    @Test
    public void servicoComEquipamentoMaisBarato(){
        String jpql = "SELECT s FROM Servico s, Equipamento e WHERE "
                + "e MEMBER OF s.equipamentos AND (s.status = ?1 OR s.status = ?2) AND (e.custoPecas + e.maoObra) = "
                + "SELECT MIN(emin.custoPecas + emin.maoObra) FROM Equipamento emin WHERE emin.servico.status = ?1 OR emin.servico.status = ?2";
        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        query.setParameter(1, Status.FINALIZADO);
        query.setParameter(2, Status.ENTREGUE);
        
        Servico servico = query.getSingleResult();
        assertEquals(2L, servico.getId());

    }
    
    @Test
    public void servicosComMaisDeUmEquipamento(){
        String jpql = "SELECT s FROM Servico s WHERE SIZE(s.equipamentos) > 1";
        
        TypedQuery<Servico> query = em.createQuery(jpql, Servico.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        
        List<Servico> servicos = query.getResultList();
        assertEquals(2, servicos.get(0).getEquipamentos().size());
        assertEquals(3, servicos.get(1).getEquipamentos().size());
    }
}
