package entidades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import utils.Status;

public class Main {

    public static void main(String[] args) {
        Cliente c1 = preencherCliente("Bia","bia.teste@email.com", new Date(1997, 6, 5) , "123546");
        Funcionario func = preencherFuncionario("cicrano", "cicrano.22@email.com", new Date(1990, 11, 15), "Gerente", "2019SO04");
        Funcionario func2 = preencherFuncionario("Fulano", "fulanoTeste@email.com", new Date(1990, 12, 28), "Técnico", "2019SO26");
        Telefone t1 = preencherTelefone(81, "32683268");
        t1.setCliente(c1);
        Telefone t2 = preencherTelefone(81, "32686584");
        t2.setCliente(c1);
        Endereco end1 = preencherEndereco("123", "Recife", "casa", 54, "rua", "bairroTeste");
        end1.setCliente(c1);
        
        c1.setEndereco(end1);
        c1.setTelefones(t1);
        c1.setTelefones(t2);
        
        Servico serv = preencherServico(Status.ABERTO, new Date(2019, 9, 5), null, new Date(2019, 9, 10));
        
        serv.setCliente(c1);
        serv.setFuncionario(func);
        c1.setServicos(serv);
        func.setServicos(serv);
        
        Equipamento eq = preencherEquipamento("Samsung", "Celular já com tela trincada", "S10", "123klmy7", "Botão de volume não funciona", "Mal encaixe da peça", 12, 0);
        
        eq.setServico(serv);
        eq.setFuncionario(func2);
        serv.setEquipamentos(eq);
        
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

    private static Cliente preencherCliente(String nome, String email, Date nasc, String cpf) {
        Cliente c  = new Cliente();
        c.setNome(nome);
        c.setEmail(email);
        c.setDataNasc(nasc);
        c.setCpf(cpf);
        return c;
    }
    
    private static Funcionario preencherFuncionario(String nome, String email, Date nasc, String cargo, String matricula) {
        Funcionario f  = new Funcionario();
        f.setNome(nome);
        f.setEmail(email);
        f.setDataNasc(nasc);
        f.setMatricula(matricula);
        f.setCargo(cargo);
        return f;
    }

    private static Endereco preencherEndereco(String cep, String cidade, String complemento, int numero, String rua, String bairro) {
        Endereco end = new Endereco();
        end.setCep(cep);
        end.setCidade(cidade);
        end.setComplemento(complemento);
        end.setNumero(numero);
        end.setRua(rua);
        end.setBairro(bairro);
        
        return end;
    }

    private static Telefone preencherTelefone(int ddd, String numero) {
        Telefone t = new Telefone();
        t.setDdd(ddd);
        t.setNumero(numero);
        
        return t;
    }
    
    private static Equipamento preencherEquipamento(String marca, String descricao, String modelo, String serie, String defeito, String solucao, double maoObra, double custoPecas){
        Equipamento eq = new Equipamento();
        eq.setDefeito(defeito);
        eq.setMarca(marca);
        eq.setSerie(serie);
        eq.setModelo(modelo);
        eq.setDescricao(descricao);
        eq.setSolucao(solucao);
        eq.setSerie(serie);
        eq.setMaoObra(maoObra);
        eq.setCustoPecas(custoPecas);
        
        return eq;
    }

    private static Servico preencherServico(Status status, Date inicio, Date fim, Date prevFim) {
        Servico s = new Servico();
        s.setStatus(status);
        s.setInicio(inicio);
        s.setFim(fim);
        s.setPrevFim(prevFim);
        
        return s;
    }
}
