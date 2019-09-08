package testes;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Equipamento;
import entidades.Pessoa;
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
import utils.Status;

public class ClienteCrudTest extends GenericTest {

    @Test
    public void persistirCliente() {
        logger.info("Executando persistirCliente()");
        Cliente cliente = criarCliente();
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
        Cliente cliente = em.find(Cliente.class, id);
        cliente.setEmail(novoEmail);
        List<Telefone> telefones = new ArrayList();
        telefones.add(telefone);
        cliente.setTelefones(telefones);
        em.flush();
        
        String jpql = "SELECT c FROM Pessoa c WHERE c.id = ?1";
        TypedQuery<Cliente> query = em.createQuery(jpql, Cliente.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS); 
        query.setParameter(1, id);
        cliente = query.getSingleResult();
        
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
        List<Telefone> telefones = new ArrayList();
        telefones.add(telefone);
        cliente.setTelefones(telefones);
        
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
        cliente.setEndereco(criarEndereco());
        
        List<Servico> servicos = criarServicos();
        cliente.setServicos(servicos);

        return cliente;
    }

    private List<Telefone> preencherTelefone(String numero) {
        List<Telefone> list = new ArrayList();
        Telefone t1 = new Telefone();
        t1.setDdd(81);
        t1.setNumero(numero);
        list.add(t1);
        return list;
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
    
    public List<Servico> criarServicos(){
        List<Servico> servicos = new ArrayList();
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
        
        servicos.add(servico);
        return servicos;
    }
    public List<Equipamento> criarEquipamentos(){
        List<Equipamento> lista = new ArrayList();
        Equipamento equi = new Equipamento();
        equi.setModelo("xrr-54");
        equi.setCustoPecas(62.50);
        equi.setDefeito("Tela trincada");
        equi.setMarca("samsung");
        equi.setSerie("123456");
        equi.setDescricao("Celular com arranhão na parte lateral direita");
        lista.add(equi);
        return lista;
    }
}
