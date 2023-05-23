/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author rodrigo
 */
public class ArancelDTO {
        public Long id;
        public String nombre;
        public String type;
        public String nombreCategoria;
        
       public ArancelDTO(Long id, String nombre, String type, String nombreCategoria){
           this.id = id;
           this.nombre = nombre;
           this.type = type;
           this.nombreCategoria  = nombreCategoria;
       }
        
}
