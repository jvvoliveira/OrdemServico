package entidades;

import java.io.Serializable;
import java.util.ArrayList;
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
    
    @Column(name = "FUNC_MATRICULA", length = 10, nullable = false, unique = true)
    private String matricula;
    
    @Column(name = "FUNC_CARGO", length = 30, nullable = false)
    private String cargo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "funcionario")
    private List<Servico> servicos;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "funcionario")
    private List<Equipamento> equipamentos;

    public Funcionario(){
        this.servicos = new ArrayList();
        this.equipamentos = new ArrayList();
    }
    
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

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(Servico servico) {
        this.servicos.add(servico);
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }
    
    public void setEquipamentos(Equipamento equipamento) {
        this.equipamentos.add(equipamento);
    }
    
}
