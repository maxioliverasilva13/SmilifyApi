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
public class PacienteDTO {
    public Long id;
    public String nombre;
    public String apellido;
    public String telefono;
    public String correo;
    public String direccion;
    public Date fechaDeNacimiento;
    public boolean activo;
    
    
    public PacienteDTO(Long id, String nombre, String apellido, String telefono, String correo, String direccion,Date fechaDeNacimiento, boolean activo){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.activo = activo;
    }
}
