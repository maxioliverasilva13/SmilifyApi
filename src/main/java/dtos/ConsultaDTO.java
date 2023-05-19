/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author rodrigo
 */
public class ConsultaDTO {
    public Long id;
    public String descripcion;
    public Boolean pago;
    public PacienteDTO paciente;
    
     public ConsultaDTO(Long id, String descripcion, Boolean pago, PacienteDTO paciente){
        this.id = id;
        this.descripcion = descripcion;
        this.pago = pago;
        this.paciente = paciente;
    }
}
