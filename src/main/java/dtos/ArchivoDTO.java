/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author mandi
 */
public class ArchivoDTO {
    public Long id;
    public String tipo;
    public String url;
    public PacienteDTO paciente;

    public ArchivoDTO(Long id, String tipo, String url, PacienteDTO paciente) {
        this.id = id;
        this.tipo = tipo;
        this.url = url;
        this.paciente = paciente;
    }
}
