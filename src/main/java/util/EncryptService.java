/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author rodrigo
 */
public class EncryptService {
    
    
    public static String encryptPass(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger bigInt = new BigInteger(1,messageDigest);
            return bigInt.toString(16);

        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
     
    }
}
