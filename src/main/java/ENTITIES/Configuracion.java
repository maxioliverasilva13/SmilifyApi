/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author rodrigo
 */
@Entity
public class Configuracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    @Basic
    private double PrecioPorOrden;
    
    @OneToOne
    private Usuario usuario;
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }
    
    public double getPrecioPorOrden(){
        return this.PrecioPorOrden;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario; 
    }
     public void setPrecioPorOrden(double precioPorOrden){
        this.PrecioPorOrden = precioPorOrden;
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
        if (!(object instanceof Configuracion)) {
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Configuracion[ id=" + id + " ]";
    }
    
}
