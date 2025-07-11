package com.ferreteriapfeifer.ferreteria_api.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "clave-super-secreta-para-mi-ferreteria-1234567890";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;


    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String rol, Persona persona) {
        return Jwts.builder()
                .setSubject(persona.getEmail())
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valida el token de Firebase, retorna true si es válido

    public boolean isTokenValid(String token) {
        try {
            FirebaseAuth.getInstance().verifyIdToken(token);
            return true;
        } catch (Exception e) {
            // Token inválido o expirado
            return false;
        }
    }

    // Extrae el email del usuario del token de Firebase
    public String extractUsername(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            return decodedToken.getEmail();
        } catch (Exception e) {
            return null;
        }
    }

    // Extrae el rol si lo tienes guardado como custom claim en Firebase, o retorna un valor por defecto
    public String extractRol(String token) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
            Object role = decodedToken.getClaims().get("role");
            return role != null ? role.toString() : "CLIENTE"; 
        } catch (Exception e) {
            return "CLIENTE";
        }
    }
}
