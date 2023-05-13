/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rodrigo
 */
@Entity
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
     
    @Column
    @Basic
    @Temporal(TemporalType.DATE)
    private Date fecha;
    
    @Column
    @Basic
    String estado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Paciente paciente;
    
    
  
    @OneToOne(mappedBy="reserva")
    Consulta consulta;
     
    
    

    public Long getId() {
        return id;
    }
    
    public Date getFecha(){
        return this.fecha;
    }
    
    public String  getEstado(){
        return this.estado;
    }
    
    public Paciente getPaciente(){
        return this.paciente;
    }
     public Consulta getConsulta(){
        return this.consulta;
    }
    
    
    

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFecha(Date fecha){
        this.fecha = fecha;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
    }
     
    public void setConsulta(Consulta consulta){
        this.consulta = consulta;
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
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Reserva[ id=" + id + " ]";
    }
    
}
