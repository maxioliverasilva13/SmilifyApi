package dtos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author rodrigo
 */
public class ResponseMessage {
    public int statusCode;
    public String message;
    
    public ResponseMessage(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}
