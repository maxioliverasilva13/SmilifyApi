/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author rodrigo
 */
@Entity
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    @Basic
    String tipo;
    
    @Column
    @Basic
    String url;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente;
    
    
    public Long getId() {
        return id;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public String getUrl(){
        return this.url;
    }
    
    public Paciente getPaciente(){
        return this.paciente;
    }
   
    public void setId(Long id) {
        this.id = id;
    }
    
     
    public void setTipo(String tipo){
        this.tipo = tipo; 
    }
    
    public void setUrl(String url){
        this.url = url;
    }
    
    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
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
        if (!(object instanceof Archivo)) {
            return false;
        }
        Archivo other = (Archivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Archivo[ id=" + id + " ]";
    }
    
}
