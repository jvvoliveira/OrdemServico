package testes;

import entidades.Cliente;
import entidades.Endereco;
import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Servico;
import entidades.Telefone;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import utils.Status;

public class GenericTest {

    protected static EntityManagerFactory emf;
    protected static Logger logger;
    protected EntityManager em;
    protected EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);
        //logger.setLevel(Level.SEVERE);
        emf = Persistence.createEntityManagerFactory("ordemservico");
        DbUnitUtil.inserirDados();
    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        beginTransaction();
    }

    @After
    public void tearDown() {
        commitTransaction();
        em.close();
    }

    private void beginTransaction() {
        et = em.getTransaction();
        et.begin();
    }

    private void commitTransaction() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            fail(ex.getMessage());
        }
    }

    protected Date getData(Integer dia, Integer mes, Integer ano) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        return c.getTime();
    }
    
    protected Cliente criarCliente(String nome, String email, String cpf) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setEmail(email);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1999);
        c.set(Calendar.MONTH, Calendar.MARCH);
        c.set(Calendar.DAY_OF_MONTH, 14);
        cliente.setDataNasc(c.getTime());

        cliente.setCpf("534.585.765-40");

        cliente.setTelefones(preencherTelefone("32683268", cliente));
        Endereco end = criarEndereco("bairro", "cidade", "cep", "rua", 12, "complemento");
        end.setCliente(cliente);
        cliente.setEndereco(end);
       
        Servico serv = criarServico(Status.ABERTO);
        serv.setCliente(cliente);
        serv.setFuncionario(criarFuncionario("testeCicrano", "Técnico", "cicrano@empresa.com", "1236456"));
        cliente.setServicos(serv);

        return cliente;
    }

    protected Telefone preencherTelefone(String numero, Cliente cliente) {
        Telefone t1 = new Telefone();
        t1.setDdd(81);
        t1.setNumero(numero);
        t1.setCliente(cliente);
        return t1;
    }

    protected Endereco criarEndereco(String bairro, String cidade, String cep, String rua, int numero, String complemento) {
        Endereco endereco = new Endereco();
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setCep(cep);
        endereco.setNumero(550);
        endereco.setRua(rua);
        endereco.setComplemento(complemento);
        return endereco;
    }

    protected Servico criarServico(Status status) {
        Servico servico = new Servico();
        servico.setStatus(status);
        
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

        return servico;
    }

    protected Equipamento criarEquipamento(String modelo, String defeito, String marca, String serie, String descricao) {
        Equipamento equi = new Equipamento();
        equi.setModelo(modelo);
        equi.setCustoPecas(62.50);
        equi.setDefeito(defeito);
        equi.setMarca(marca);
        equi.setSerie(serie);
        equi.setDescricao(descricao);
        return equi;
    }

    protected Funcionario criarFuncionario(String nome, String cargo, String email, String matricula) {
        Funcionario func = new Funcionario();
        func.setNome(nome);
        func.setCargo(cargo);
        func.setDataNasc(new Date(1999, 5, 31));
        func.setEmail(email);
        func.setMatricula(matricula);

        return func;
    }
}