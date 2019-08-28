package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa();
        preencherUsuario(pessoa);
        Funcionario func = new Funcionario();
        preencherFuncionario(func);
        
        Funcionario tec1 = new Funcionario();
        Funcionario tec2 = new Funcionario();
        preencherTecnico(tec1, tec2);
        
        Servico servico = new Servico();
        servico.setCliente(pessoa);
        servico.setAtendente(func);
        
        Equipamento equip1 = new Equipamento();
        equip1.setModelo("X45");
        equip1.setDefeito("Ta ruim");
        equip1.setSerie("458asd");
        equip1.setCustoPecas(45.52);
        equip1.setMaoObra(36.98);
        equip1.setServico(servico);
        equip1.setTecnico(tec2);
        
        Equipamento equip2 = new Equipamento();
        equip2.setModelo("S6");
        equip2.setDefeito("Telatrincada");
        equip2.setSerie("As54d1");
        equip2.setCustoPecas(500.52);
        equip2.setMaoObra(50.98);
        equip2.setServico(servico);
        equip2.setTecnico(tec1);
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("ordemservico");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsável pelo gerenciamento de transação.
            et.begin();
            em.persist(pessoa);
            em.persist(func);
            em.persist(tec1);
            em.persist(tec2);
            em.persist(servico);
            em.persist(equip1);
            em.persist(equip2);
            et.commit();
        } catch (Exception ex) {
            System.out.println(ex);
            if (et != null) {
                et.rollback();
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    private static void preencherUsuario(Pessoa pessoa) {
        pessoa.setNome("Citana");
        pessoa.setCpf("145.397.025-31");
        pessoa.setEndereco(preencherEndereco("52090-260", "Recife", "Casa", 7574, "Avenida Norte"));

        List<Telefone> telefones = new ArrayList();
        telefones.add(preencherTelefone("8196683265", pessoa));
        telefones.add(preencherTelefone("8132683268", pessoa));
        telefones.add(preencherTelefone("9468465335", pessoa));
        pessoa.setTelefones(telefones);
    }

    private static void preencherFuncionario(Funcionario pessoa) {
        pessoa.setNome("Sicratrano");
        pessoa.setCpf("643.225.215-06");
        pessoa.setEndereco(preencherEndereco("55982-693", "Recife", "Casa", 306, "Casa Forte"));
        pessoa.setMatricula("123456");
        pessoa.setCargo("Desenvolvedor");

        List<Telefone> telefones = new ArrayList();
        telefones.add(preencherTelefone("8162986598", pessoa));
        telefones.add(preencherTelefone("8199996666", pessoa));
        pessoa.setTelefones(telefones);
    }
    
        private static void preencherTecnico(Funcionario tec1, Funcionario tec2) {
        tec1.setNome("Beltrano");
        tec1.setCpf("643.225.215-06");
        tec1.setEndereco(preencherEndereco("55422-693", "Recife", "casa", 56, "Casa Amarela"));
        tec1.setMatricula("51644");
        tec1.setCargo("Tecnico");

        List<Telefone> telefones = new ArrayList();
        telefones.add(preencherTelefone("41545458", tec1));
        
        tec2.setTelefones(telefones);
        tec2.setNome("Zurilano");
        tec2.setCpf("643.225.215-06");
        tec2.setEndereco(preencherEndereco("55422-693", "Olinda", "casa", 56, "Peixinhos"));
        tec2.setMatricula("5454");
        tec2.setCargo("Tecnico");

        telefones = new ArrayList();
        telefones.add(preencherTelefone("75454589", tec2));
        telefones.add(preencherTelefone("47565558", tec2));
        tec2.setTelefones(telefones);
    }

    private static Endereco preencherEndereco(String cep, String cidade, String complemento, int numero, String rua) {
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setCidade(cidade);
        endereco.setComplemento(complemento);
        endereco.setNumero(numero);
        endereco.setRua(rua);
        return endereco;
    }

    private static Telefone preencherTelefone(String telefone, Pessoa pessoa) {
        Telefone t = new Telefone();
        t.setNumero(telefone);
        t.setPessoa(pessoa);
        return t;
    }
}
