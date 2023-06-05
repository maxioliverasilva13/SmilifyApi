/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author maximilianoolivera
 */
public class CreateDienteInfoDTO {
    private String descripcion;
    private String fecha;
    private String type;
    private String dienteId;
    private int pacienteId;

    public String getDescripcion() {
        return descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public String getType() {
        return type;
    }

    public String getDienteId() {
        return dienteId;
    }

    public int getPacienteId() {
        return pacienteId;
    }
    
    
}
