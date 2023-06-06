/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import ENTITIES.Archivo;
import ENTITIES.Consulta;
import ENTITIES.Paciente;
import ENTITIES.Reserva;
import ENTITIES.Tratamiento;
import java.util.List;
import java.util.Set;

/**
 *
 * @author maximilianoolivera
 */
public class PacienteInfoDTO {

    public Paciente pacienteInfo;
    public Set<Reserva> reservas;
    public Set<Tratamiento> tratamientos;
    public Set<Archivo> archivos;
    public Set<Consulta> consultas;

    public Set<Archivo> getArchivos() {
        return archivos;
    }

    public void setArchivos(Set<Archivo> archivos) {
        this.archivos = archivos;
    }
    
    

    
    
    public Paciente getPacienteInfo() {
        return pacienteInfo;
    }

    public void setPacienteInfo(Paciente pacienteInfo) {
        this.pacienteInfo = pacienteInfo;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Set<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(Set<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public Set<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(Set<Consulta> consultas) {
        this.consultas = consultas;
    }
    
    

    
    
}
