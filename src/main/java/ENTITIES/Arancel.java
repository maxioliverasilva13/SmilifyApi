/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author rodrigo
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TYPE")
public class Arancel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    @Basic
    String nombre;
    

        
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "arancel")
    private Set<Consulta> consultas;
    
    @OneToMany(mappedBy = "arancelLab")
    private Set<Consulta> consultasArancelLab;
    
    @ManyToOne()
    private CategoriaArancel categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Consulta> listarConsultasLab() {
        return consultasArancelLab;
    }

    public void setConsultasArancelLab(Set<Consulta> consultasArancelLab) {
        this.consultasArancelLab = consultasArancelLab;
    }
    
    public String getNombre(){
        return this.nombre;
    }

    public Set<Consulta> listarAllConsultas() {
        return consultas;
    }

    public void setConsultas(Set<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setCategoria(CategoriaArancel categoria){
        this.categoria = categoria;
     }
    
    public CategoriaArancel getCategoria(){
        return this.categoria;
    }
    
    public String getType() {
        return "Arancel";
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Arancel)) {
            return false;
        }
        Arancel other = (Arancel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Arancel[ id=" + id + " ]";
    }
    
}
