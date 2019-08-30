package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

    public static void main(String[] args) {
        Cliente c1 = preencherCliente("Bia", "123546");
        Funcionario func = preencherFuncionario("cicrano", "2019");
        Telefone t1 = preencherTelefone("32683268", c1);
        Telefone t2 = preencherTelefone("32686584", c1);
        Endereco end1 = preencherEndereco("123", "Recife", "casa", 54, "rua", c1);
//        c1.setEndereco(end1);
        List<Telefone> ts = new ArrayList();
        ts.add(t1);
        ts.add(t2);
        c1.setTelefones(ts);
        
        Servico serv = new Servico();
        serv.setCliente(c1);
        serv.setFuncionario(func);
        
        Equipamento eq = preencherEquipamento("Samsung", "S10", "uefhru", "explosão", serv);
        
        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("ordemservico");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsável pelo gerenciamento de transação.
            et.begin();
            em.persist(eq);
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

    private static Cliente preencherCliente(String nome, String cpf) {
        Cliente c  = new Cliente();
        c.setNome(nome);
        c.setCpf(cpf);
        return c;
    }
    
    private static Funcionario preencherFuncionario(String nome, String matricula) {
        Funcionario f  = new Funcionario();
        f.setNome(nome);
        f.setMatricula(matricula);
        return f;
    }

    private static Endereco preencherEndereco(String cep, String cidade, String complemento, int numero, String rua, Cliente cliente) {
        Endereco end = new Endereco();
        end.setCep(cep);
        end.setCidade(cidade);
        end.setCliente(cliente);
        
        return end;
    }

    private static Telefone preencherTelefone(String numero, Cliente cliente) {
        Telefone t = new Telefone();
        t.setCliente(cliente);
        t.setNumero(numero);
        
        return t;
    }
    
    private static Equipamento preencherEquipamento(String marca, String modelo, String serie,String defeito, Servico servico){
        Equipamento eq = new Equipamento();
        eq.setDefeito(defeito);
        eq.setMarca(marca);
        eq.setSerie(serie);
        eq.setModelo(modelo);
        eq.setServico(servico);
        
        return eq;
    }
}
