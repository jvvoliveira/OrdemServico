package entidades;

import entidades.Equipamento;
import entidades.Funcionario;
import entidades.Pessoa;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import utils.Status;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-08-28T23:09:00")
@StaticMetamodel(Servico.class)
public class Servico_ { 

    public static volatile SingularAttribute<Servico, Pessoa> cliente;
    public static volatile SingularAttribute<Servico, Funcionario> atendente;
    public static volatile SingularAttribute<Servico, Double> MaoDeObra;
    public static volatile SingularAttribute<Servico, Date> inicio;
    public static volatile SingularAttribute<Servico, Long> id;
    public static volatile SingularAttribute<Servico, Date> fim;
    public static volatile SingularAttribute<Servico, Double> custoPecas;
    public static volatile ListAttribute<Servico, Equipamento> equipamentos;
    public static volatile SingularAttribute<Servico, Status> status;
    public static volatile SingularAttribute<Servico, Date> prevFim;

}