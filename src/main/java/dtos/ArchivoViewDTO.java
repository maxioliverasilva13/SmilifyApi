/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author mandi
 */
public class ArchivoViewDTO {
    public Long id;
    public String tipo;
    public String url;
    public Long paciente_id;

    public ArchivoViewDTO(Long id, String tipo, String url, Long paciente_id) {
        this.id = id;
        this.tipo = tipo;
        this.url = url;
        this.paciente_id = paciente_id;
    }
}
