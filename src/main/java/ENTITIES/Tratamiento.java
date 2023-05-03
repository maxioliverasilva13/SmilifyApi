/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author rodrigo
 */
@Entity
public class Tratamiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    @Basic
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;
    
    @Column(nullable=true)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tratamiento", fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Consulta> consultas; 
     
    
    

    public Long getId() {
        return id;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
    
    public List<Consulta> getConsultas(){
        return this.consultas;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    public void setConsultas(List<Consulta> consultas){
        this.consultas = consultas;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Tratamiento)) {
            return false;
        }
        Tratamiento other = (Tratamiento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Tratamiento[ id=" + id + " ]";
    }
    
}
