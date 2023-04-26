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
import java.util.function.Function;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author rodrigo
 */
public class JWT {

    public static String KEY = "SECRETCLAVE";
    private static JWT instance;
    static byte[] secret = KEY.getBytes();

    public JsonObject encode(Map<String, Object> claims) {
        long current_time = System.currentTimeMillis();
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, KEY.getBytes()).setIssuedAt(new Date(current_time)).setExpiration(new Date(current_time + 86400000)).addClaims(claims).compact();
        JsonObject json = Json.createObjectBuilder().add("result", jwt).build();

        return json;
    }

    public Long getUserIdByToken(String token) {
        Claims claims = extractClaims(token);
        Long id = claims.get("id", Long.class);
        return id;

    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claim = extractClaims(token);
        return claimResolver.apply(claim);
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(KEY.getBytes()).parseClaimsJws(token).getBody();
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
