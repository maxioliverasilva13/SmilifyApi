/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rodrigo
 */
@Entity
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column 
    @Basic
    private String nombre;
    
    @Column 
    @Basic
    private String apellido;
    
    @Column 
    @Basic
    private String direccion;
    
    @Column 
    @Basic
    private String telefono;
    
    @Column 
    @Basic
    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Archivo> archivos = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Reserva> reservas = new ArrayList<>();
    
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Consulta> consultas = new ArrayList<>();
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Tratamiento> tratamientos = new ArrayList<>(); 
    
     

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
     public String getApellido(){
        return this.apellido;
    }
      public String getDireccion(){
        return this.direccion;
    }
    public String getTelefono(){
        return this.telefono;
    }
    
     public Date getFechaDeNacimiento(){
        return this.fechaDeNacimiento;
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }
   
    public List<Archivo> getArchivo(){
        return this.archivos;
    }
    
    
    
    public List<Reserva> getReservas(){
        return this.reservas;
    }
    
    public List<Consulta> getConsultas(){
        return this.consultas;
    }
    
    public List<Tratamiento> getTratamientos(){
        return this.tratamientos;
    }
   
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    public void setFechaDeNacimiento(Date fechaDeNacimiento){
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }
    
    public void setArchivos(List<Archivo> archivos){
        this.archivos = archivos;
    }
    
    public void setReservas(List<Reserva> reservas){
        this.reservas = reservas;
    }
    
    public void setConsultas(List<Consulta> consultas){
        this.consultas = consultas;
    }
    
    public void setTratamientos(List<Tratamiento> tratamientos){
        this.tratamientos = tratamientos;
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
        if (!(object instanceof Paciente)) {
            return false;
        }
        Paciente other = (Paciente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Paciente[ id=" + id + " ]";
    }
    
}
