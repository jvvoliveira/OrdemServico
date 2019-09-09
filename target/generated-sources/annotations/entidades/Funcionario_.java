package entidades;

import entidades.Equipamento;
import entidades.Servico;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-08T21:52:30")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ extends Pessoa_ {

    public static volatile ListAttribute<Funcionario, Servico> servicos;
    public static volatile SingularAttribute<Funcionario, String> matricula;
    public static volatile SingularAttribute<Funcionario, String> cargo;
    public static volatile ListAttribute<Funcionario, Equipamento> equipamentos;

}