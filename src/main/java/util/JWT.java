/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author rodrigo
 */
public class JWT {
    private String KEY = "mi_clave";
    private static  JWT instance;

    public JsonObject encode(Map<String, Object> claims){
        long current_time = System.currentTimeMillis();
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256,KEY).setIssuedAt(new Date(current_time)).setExpiration(new Date(current_time + 86400000)).addClaims(claims).compact();
        JsonObject json = Json.createObjectBuilder().add("result",jwt).build();
          

        return json;
    }
    
    public Long getUserIdByToken(String token){

        Claims claims = Jwts.parser()
                        .setSigningKey(KEY.getBytes()) // clave secreta para verificar la firma del JWT
                        .parseClaimsJws(token)
                        .getBody();
        Long id = claims.get("id", Long.class);
        return id;

    }
   
    public static JWT getInstance() {
        if (instance == null) {
            instance = new JWT();
        }
        return instance;
    }
    
    private JWT() {
        
    }
}
