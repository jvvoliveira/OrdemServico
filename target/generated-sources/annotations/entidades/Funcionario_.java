package entidades;

import entidades.Equipamento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-08-28T23:09:00")
@StaticMetamodel(Funcionario.class)
public class Funcionario_ extends Pessoa_ {

    public static volatile SingularAttribute<Funcionario, String> matricula;
    public static volatile SingularAttribute<Funcionario, String> cargo;
    public static volatile ListAttribute<Funcionario, Equipamento> equipamentos;

}