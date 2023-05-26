/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author maximilianoolivera
 */
public class GetDienteInfoDTO {
    private int pacienteId;
    private String type;
    private int dienteId;

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDienteId() {
        return dienteId;
    }

    public void setDienteId(int dienteId) {
        this.dienteId = dienteId;
    }
}
