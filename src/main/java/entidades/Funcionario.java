package entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "F")
public class Funcionario extends Pessoa implements Serializable {
    
    @Column(name = "COL_MATRICULA", length = 10, nullable = false)
    private String matricula;
    
    @Column(name = "COL_MATRICULA", length = 30, nullable = false)
    private String cargo;

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
    
}
