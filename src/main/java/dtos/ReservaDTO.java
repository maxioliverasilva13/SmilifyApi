/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.Date;

/**
 *
 * @author rodrigo
 */
public class ReservaDTO {
    public Long id; 
    public String estado;
    public Date fecha;
    public PacienteDTO paciente;
    
    public ReservaDTO(){
    }
    
    public ReservaDTO(Long id, String estado, Date fecha ,PacienteDTO paciente ){
        this.id = id;
        this.estado = estado;
        this.fecha = fecha;
        this.paciente = paciente;
    }
}
