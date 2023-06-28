/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    private String correo;

    @Column
    @Basic
    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;

    @Column
    @Basic
    @Temporal(TemporalType.DATE)
    private Date fechaDeAlta;
    
    @Column
    @Basic
    private String  datosClinicos;
    
    @Column
    @Basic
    private String  ocupacion; 
    
    
    

    @Column
    @Basic
    private Boolean tieneAlta = false;
  
    @Column 
    @Basic
    private Boolean activo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<Archivo> archivos = Collections.emptySet();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<Reserva> reservas = Collections.emptySet();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<Consulta> consultas = Collections.emptySet();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paciente", fetch = FetchType.EAGER)
    private Set<Tratamiento> tratamientos = Collections.emptySet();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pacienteInfo", fetch = FetchType.EAGER)
    private Set<DienteInfo> infoDientes = Collections.emptySet();

    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getApellido() {
        return this.apellido;
    }
    
    public Set<DienteInfo> obtenerDientesInfo() {
        return this.infoDientes;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public Date getFechaDeNacimiento() {
        return this.fechaDeNacimiento;
    }
     
     public boolean getActivo(){
        return this.activo;
    }
    
    public Usuario getUsuario(){
        return this.usuario;
    }
    
    public Usuario getUsuarioInfoData(){
        return this.usuario;
    }

    public Set<Archivo> getArchivo() {
        return this.archivos;
    }

    public Set<Reserva> getReservas() {
        return this.reservas;
    }

    public Set<Consulta> getConsultas() {
        return this.consultas;
    }

    public Set<Tratamiento> getTratamientos() {
        return this.tratamientos;
    }
    
    
    
    public String getDatosClinicos(){
        return this.datosClinicos;
    }
    
    public String getOcupacion(){
        return this.ocupacion;
    }
    
    
    public void setDatosClinicos(String datosClinicos){
        this.datosClinicos = datosClinicos;
    }
    
    public void setOcupacion(String ocupacion){
        this.ocupacion = ocupacion;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    
    public void setActivo(boolean activo){
        this.activo = activo;
    }
    
    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void setConsultas(Set<Consulta> consultas) {
        this.consultas = consultas;
    }

    public void setTratamientos(Set<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Date getFechaDeAlta() {
        return fechaDeAlta;
    }

    public void setFechaDeAlta(Date fechaDeAlta) {
        this.fechaDeAlta = fechaDeAlta;
    }

    public Boolean getTieneAlta() {
        return tieneAlta;
    }

    public void setTieneAlta(Boolean tieneAlta) {
        this.tieneAlta = tieneAlta;
    }

    public Set<DienteInfo> getInfoDientes() {
        return infoDientes;
    }

    public void setInfoDientes(Set<DienteInfo> infoDientes) {
        this.infoDientes = infoDientes;
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
