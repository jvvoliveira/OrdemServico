package entidades;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;
import utils.Status;

public class Main {

    public static void main(String[] args) {
        Cliente c1 = preencherCliente("Bia", "bia.teste@email.com", getData(5, 6, 1997), "123.546.758-74");
        Funcionario func = preencherFuncionario("cicrano", "cicrano.22@email.com", getData(15, 11, 1990), "Gerente", "201909OS4");
        Funcionario func2 = preencherFuncionario("Fulano", "fulanoTeste@email.com", getData(28, 12, 1990), "Técnico", "201976OS6");
        Telefone t1 = preencherTelefone("81", "32683268");
        Telefone t2 = preencherTelefone("81", "32686584");
        Endereco end1 = preencherEndereco("12345678", "Recife", "casa", 54, "rua", "bairroTeste");

        c1.setEndereco(end1);
        c1.addTelefones(t1);
        c1.addTelefones(t2);

        Servico serv = preencherServico(Status.ABERTO, getData(5, 9, 2019), null, getData(19, 12, 2019));

        serv.setCliente(c1);
        serv.setFuncionario(func);

        Equipamento eq = preencherEquipamento("Samsung", "Celular já com tela trincada", "S10", "123klmy7", "Botão de volume não funciona", "Mal encaixe da peça", 12, 0);

        func2.addEquipamentos(eq);
        serv.addEquipamento(eq);

        EntityManagerFactory emf = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            //EntityManagerFactory realiza a leitura das informações relativas à "persistence-unit".
            emf = Persistence.createEntityManagerFactory("ordemservico");
            em = emf.createEntityManager(); //Criação do EntityManager.
            et = em.getTransaction(); //Recupera objeto responsável pelo gerenciamento de transação.
            et.begin();
            em.persist(serv);
            et.commit();
        } catch (ConstraintViolationException e) {
            System.out.println("------------ERRO---------");
            System.out.println(e.getConstraintViolations().toString());
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
        Cliente c = new Cliente();
        c.setNome(nome);
        c.setEmail(email);
        c.setDataNasc(nasc);
        c.setCpf(cpf);
        return c;
    }

    private static Funcionario preencherFuncionario(String nome, String email, Date nasc, String cargo, String matricula) {
        Funcionario f = new Funcionario();
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

    private static Telefone preencherTelefone(String ddd, String numero) {
        Telefone t = new Telefone();
        t.setDdd(ddd);
        t.setNumero(numero);

        return t;
    }

    private static Equipamento preencherEquipamento(String marca, String descricao, String modelo, String serie, String defeito, String solucao, double maoObra, double custoPecas) {
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
    
    private static Date getData(Integer dia, Integer mes, Integer ano) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.DAY_OF_MONTH, dia);
        return c.getTime();
    }
}
