/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author maximilianoolivera
 */
public class CreateArancelDTO {
    private String nombre;
    private int categoriaId;
    private float precio;
    private int cantOrdenes;
    private String type;

//    public CreateArancelDTO(String nombre, int categoriaId, int precio, int cantOrdenes, String type) {
//        this.nombre = nombre;
//        this.categoriaId = categoriaId;
//        this.precio = precio;
//        this.cantOrdenes = cantOrdenes;
//        this.type = type;
//    }
//    
    

    public String getNombre() {
        return nombre;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public float getPrecio() {
        return precio;
    }

    public int getCantOrdenes() {
        return cantOrdenes;
    }

    public String getType() {
        return type;
    }
    
    
}
