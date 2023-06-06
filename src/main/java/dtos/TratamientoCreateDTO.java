/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author maximilianoolivera
 */
public class TratamientoCreateDTO {
    String descripcion;
    Long paciente_id;
    String status = "activo";

    public String getDescripcion() {
        return descripcion;
    }

    public Long getPaciente_id() {
        return paciente_id;
    }

    public String getStatus() {
        return status;
    }
    
    
    
}
