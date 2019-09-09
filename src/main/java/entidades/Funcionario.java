package entidades;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa implements Serializable {
    
    @Column(name = "FUNC_MATRICULA", length = 10, nullable = true, unique = true)
    private String matricula;
    
    @Column(name = "FUNC_CARGO", length = 30, nullable = true)
    private String cargo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "cliente")
    private List<Servico> servicos;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "funcionario")
    private List<Equipamento> equipamentos;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula() {
        GregorianCalendar.getInstance().get(Calendar.YEAR);
        this.matricula = GregorianCalendar.getInstance().get(Calendar.YEAR)+this.getDataNasc().getMonth()+"OS"+this.getId();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Servico servico) {
        this.servicos.add(servico);
    }
    
    
}
