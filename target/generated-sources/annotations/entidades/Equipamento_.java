package entidades;

import entidades.Funcionario;
import entidades.Servico;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-08-28T23:09:00")
@StaticMetamodel(Equipamento.class)
public class Equipamento_ { 

    public static volatile SingularAttribute<Equipamento, Funcionario> tecnico;
    public static volatile SingularAttribute<Equipamento, Double> custoPecas;
    public static volatile SingularAttribute<Equipamento, String> modelo;
    public static volatile SingularAttribute<Equipamento, Date> inicioManutencao;
    public static volatile SingularAttribute<Equipamento, String> descricao;
    public static volatile SingularAttribute<Equipamento, String> marca;
    public static volatile SingularAttribute<Equipamento, String> defeito;
    public static volatile SingularAttribute<Equipamento, String> solucao;
    public static volatile SingularAttribute<Equipamento, Double> maoObra;
    public static volatile SingularAttribute<Equipamento, String> serie;
    public static volatile SingularAttribute<Equipamento, Long> id;
    public static volatile SingularAttribute<Equipamento, Date> fimManutencao;
    public static volatile SingularAttribute<Equipamento, Servico> servico;

}