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
