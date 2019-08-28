package entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import utils.Status;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-08-27T22:44:51")
@StaticMetamodel(Servico.class)
public class Servico_ { 

    public static volatile SingularAttribute<Servico, Double> MaoDeObra;
    public static volatile SingularAttribute<Servico, Date> inicio;
    public static volatile SingularAttribute<Servico, Long> id;
    public static volatile SingularAttribute<Servico, Date> fim;
    public static volatile SingularAttribute<Servico, Status> status;

}