/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author rodrigo
 */
@Entity
public class Usuario implements Serializable {

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
    
    @Column(unique=true)
    @Basic
    private String email;
    
    
    @Column
    @Basic
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date fechaDeNacimiento;
    
    @Column(length=9)
    @Basic
    private String celular;
    
    @Column
    @Basic
    private String avatar;
    
    
    @Column
    @Basic
    private String password;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Paciente> pacientes = new ArrayList<>();
    
    @OneToOne(mappedBy="usuario")
    Configuracion configuracion;
    
 
    
  

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
    
    public String getEmail(){
        return this.email;
    }
    
    public Date getFechaNacimiento(){
        return this.fechaDeNacimiento;
    }
    
    public String getCelular(){
        return this.celular;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public List<Paciente> getPacientes(){
        return this.pacientes;
    }
    public Configuracion getConfiguracion(){
        return this.configuracion;
    }    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setApellido(String apellido){
        this.apellido = apellido;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setFechaNacimiento(Date fecha) {
        this.fechaDeNacimiento = fecha;
    }
    
    public void setCelular(String celular){
        this.celular = celular;
    }
    
     public void setPassword(String password){ 
        this.password = password;
    }
     
     public void setAvatar(String avatar){
        this.avatar = avatar;
     }
     
     public String getAvatar(){
         return this.avatar;
     }
     
    public void setPacientes(List<Paciente> pacientes){
        this.pacientes = pacientes;
    }
    public void setConfiguracion(Configuracion configuracion){
        this.configuracion =  configuracion;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.Usuario[ id=" + id + " ]";
    }
    
}
