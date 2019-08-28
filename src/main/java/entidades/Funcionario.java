package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa implements Serializable {
    
    @Column(name = "FUNC_MATRICULA", length = 10)
    private String matricula;
    
    @Column(name = "FUNC_CARGO", length = 30)
    private String cargo;
    
    //Listas de servicos atendidos
    @OneToMany(mappedBy = "atendente", cascade = CascadeType.PERSIST)
    private List<Servico> servicos;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Servico servico) {
        this.servicos.add(servico);
    }
    
    
}
