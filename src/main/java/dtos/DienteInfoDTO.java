/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Date;

/**
 *
 * @author maximilianoolivera
 */
public class DienteInfoDTO {
    
    private Long id;

    private String dienteId;
    private String type;

    private String fecha;
    private String descripcion;

    public DienteInfoDTO(Long id, String dienteId, String type, String fecha, String descripcion) {
        this.id = id;
        this.dienteId = dienteId;
        this.type = type;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDienteId() {
        return dienteId;
    }

    public void setDienteId(String dienteId) {
        this.dienteId = dienteId;
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

    
    
    
}
