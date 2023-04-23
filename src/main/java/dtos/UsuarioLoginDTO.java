package dtos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rodrigo
 */
public class UsuarioLoginDTO {
    public String email;
    public String password;
    
    public String getEmail(){ 
         return this.email;
    }
    
    public String getPassword(){
        return this.password;  
    }
}
