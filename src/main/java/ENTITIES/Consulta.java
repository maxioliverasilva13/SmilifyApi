/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
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
public class Consulta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column
    @Basic
    private String descripcion;
    
    @Column
    @Basic
    private Double entrega;
    
    @Column
    @Basic
    private boolean pago;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Paciente paciente; 
    

    @OneToOne
    private Reserva reserva;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Tratamiento tratamiento;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Arancel arancel;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Arancel arancelLab;
    
    public Long getId() {
        return id;
    }
    
     
   public String getDescripcion(){
        return this.descripcion;
    }
    
    public boolean getPago(){
        return this.pago;
    }

    public Arancel getArancelLab() {
        return arancelLab;
    }

    public void setArancelLab(Arancel arancelLab) {
        this.arancelLab = arancelLab;
    }

   
    public Paciente getPaciente(){
        return null;
    }
    
    public Reserva getReserva(){
        return this.reserva;
    }

    public Arancel getArancel() {
        return arancel;
    }

    public void setArancel(Arancel arancel) {
        this.arancel = arancel;
    }
    
    

    public Tratamiento getTratamiento(){
        return this.tratamiento;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    public void setPago(boolean pago){
        this.pago = pago;
    }
  
     public void setPaciente(Paciente paciente){
        this.paciente = paciente;
     }
     
      public void setReserva(Reserva reserva){
        this.reserva = reserva;
     }
      
       public void setTratamiento(Tratamiento tratamiento){
        this.tratamiento = tratamiento;
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
        if (!(object instanceof Consulta)) {
            return false;
        }
        Consulta other = (Consulta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Consulta[ id=" + id + " ]";
    }

    public Double getEntrega() {
        return entrega;
    }

    public void setEntrega(Double entrega) {
        this.entrega = entrega;
    }
    
    
    
}
