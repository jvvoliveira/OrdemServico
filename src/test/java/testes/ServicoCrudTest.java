/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        Servico servico = criarServico();
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
        
        Long id = 1L; //saber ID exato do cliente
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
        
        Long id = 1L; //saber ID exato do cliente
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
        em.remove(servico);
        
        Servico checkServico = em.find(Servico.class, 1L);
        assertNull(checkServico);
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Sicrano da Silva");
        cliente.setEmail("sicrano@gmail.com");
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1999);
        c.set(Calendar.MONTH, Calendar.MARCH);
        c.set(Calendar.DAY_OF_MONTH, 14);
        cliente.setDataNasc(c.getTime());
       
        cliente.setCpf("534.585.765-40");

        cliente.setTelefones(preencherTelefone("32683268"));
        cliente.setTelefones(preencherTelefone("32689952"));
        cliente.setEndereco(criarEndereco());

        return cliente;
    }
    
    private Funcionario criarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Sicrano da Silva");
        funcionario.setEmail("sicrano@gmail.com");
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1999);
        c.set(Calendar.MONTH, Calendar.MARCH);
        c.set(Calendar.DAY_OF_MONTH, 14);
        funcionario.setDataNasc(c.getTime());
       
        funcionario.setCargo("Gerente");
        funcionario.setMatricula("123456");

        return funcionario;
    }

    private Telefone preencherTelefone(String numero) {
        Telefone t1 = new Telefone();
        t1.setDdd(81);
        t1.setNumero(numero);
        return t1;
    }

    public Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setBairro("Casa Forte");
        endereco.setCidade("Recife");
        endereco.setCep("50690-220");
        endereco.setNumero(550);
        endereco.setRua("17 de Agosto");
        endereco.setComplemento("casa");
        return endereco;
    }
    
    public Equipamento criarEquipamentos(){
        Equipamento equi = new Equipamento();
        equi.setModelo("xrr-54");
        equi.setCustoPecas(62.50);
        equi.setDefeito("Tela trincada");
        equi.setMarca("samsung");
        equi.setSerie("123456");
        equi.setDescricao("Celular com arranhão na parte lateral direita");
        return equi;
    }

    private Servico criarServico() {
        Servico servico = new Servico();
        servico.setEquipamentos(criarEquipamentos());
        servico.setStatus(Status.ABERTO);
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c.set(Calendar.DAY_OF_MONTH, 2);
        servico.setInicio(c.getTime());
        
        Calendar c2 = Calendar.getInstance();
        c2.set(Calendar.YEAR, 2019);
        c2.set(Calendar.MONTH, Calendar.SEPTEMBER);
        c2.set(Calendar.DAY_OF_MONTH, 17);
        servico.setPrevFim(c2.getTime());
        
        Cliente cliente = criarCliente();
        servico.setCliente(cliente);
        Funcionario funcionario = criarFuncionario();
        servico.setFuncionario(funcionario);
        servico.setEquipamentos(criarEquipamentos());
        
        return servico;
    }
}
