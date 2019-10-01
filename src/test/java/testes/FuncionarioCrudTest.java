package testes;

import entidades.Cliente;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Servico;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import org.eclipse.persistence.jpa.JpaCache;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static testes.GenericTest.emf;
import utils.Status;

public class FuncionarioCrudTest extends GenericTest {

    @Test
    public void persistirFuncionario() {
        logger.info("Executando persistirFuncionario()");

        Funcionario funcionario = criarFuncionario("João Funcionario", "Técnico", "jfunc@tecnico.com", "201902OS5");
        Equipamento equipamento = criarEquipamento("xcv123", "não liga", "samsung", "32fg1654", "celular com capinha");
        Equipamento equipamento2 = criarEquipamento("asd321", "não liga", "motorola", "ftg765", "celular");
        Servico servico = criarServico(Status.ABERTO);
        Cliente cliente = new Cliente();
        cliente.setNome("teste");
        cliente.setEmail("cliente@gmail.com");
        cliente.setDataNasc(getData(6, 5, 1999));

        //serviço não pode ficar sem cliente e sem funcionário atendente
        servico.setInicio(getData(25, 9, 2019));
        servico.setPrevFim(getData(12, 12, 2019));
        servico.setCliente(cliente);
        servico.setFuncionario(funcionario);
        servico.addEquipamento(equipamento);
        servico.addEquipamento(equipamento2);

        //equipamento não pode ficar sem serviço
        funcionario.addEquipamentos(equipamento);
        funcionario.addEquipamentos(equipamento2);

//        try {
            em.persist(funcionario);
            em.flush();

            assertNotNull(funcionario.getId());
            assertNotNull(funcionario.getEquipamentos().get(0).getId());
            assertNotNull(funcionario.getEquipamentos().get(1).getId());
//        } catch (ConstraintViolationException e) {
//            System.out.println("------------ERRO---------");
//            System.out.println(e.getConstraintViolations().toString());
//        }
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

        Funcionario funcionario = em.find(Funcionario.class, 3L);
        assertNotNull(funcionario);

        //retirar fk dos equipamentos desse funcionário
        String jpql = "SELECT e FROM Equipamento e WHERE e.funcionario.id = ?1";
        TypedQuery<Equipamento> query = em.createQuery(jpql, Equipamento.class);
        //obrigatoriamente ir para o banco
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setParameter(1, 3L);
        List<Equipamento> equipamentos = query.getResultList();
        equipamentos.get(0).setFuncionario(null);
        equipamentos.get(1).setFuncionario(null);
        equipamentos.get(2).setFuncionario(null);
        em.flush();

        em.remove(funcionario);

        ((JpaCache) emf.getCache()).clear();
        Funcionario checkFuncionario = em.find(Funcionario.class, 3L);
        assertNull(checkFuncionario);
    }
}
