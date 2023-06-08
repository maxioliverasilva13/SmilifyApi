/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ENTITIES;

import dtos.DienteInfoDTO;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author maximilianoolivera
 */
@Entity
public class DienteInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Basic
    private String dienteId;
    @Column
    @Basic
    private String type;

    @Column
    @Basic
    private String fecha;
    @Column
    @Basic
    private String descripcion;

    @ManyToOne
    private Paciente pacienteInfo;

    public String getDienteId() {
        return dienteId;
    }

    public void setDienteId(String dienteId) {
        this.dienteId = dienteId;
    }

    public Paciente obtenerPacienteInfo() {
        return pacienteInfo;
    }

    public void setPacienteInfo(Paciente pacienteInfo) {
        this.pacienteInfo = pacienteInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
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
        if (!(object instanceof DienteInfo)) {
            return false;
        }
        DienteInfo other = (DienteInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ENTITIES.DienteInfo[ id=" + id + " ]";
    }
    
    public DienteInfoDTO getDTO() {
        DienteInfoDTO dto = new DienteInfoDTO(this.getId(), this.getDienteId(), this.getType(), this.getFecha().toString(), this.getDescripcion());
        return dto;
    }

}
