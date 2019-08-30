package entidades;

import entidades.Endereco;
import entidades.Servico;
import entidades.Telefone;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-08-28T23:09:00")
@StaticMetamodel(Pessoa.class)
public class Pessoa_ { 

    public static volatile ListAttribute<Pessoa, Servico> servicos;
    public static volatile SingularAttribute<Pessoa, Date> dataNasc;
    public static volatile SingularAttribute<Pessoa, Endereco> endereco;
    public static volatile SingularAttribute<Pessoa, String> cpf;
    public static volatile SingularAttribute<Pessoa, String> nome;
    public static volatile SingularAttribute<Pessoa, Long> id;
    public static volatile ListAttribute<Pessoa, Telefone> telefones;
    public static volatile SingularAttribute<Pessoa, String> email;

}