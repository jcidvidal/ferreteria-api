package com.ferreteriapfeifer.ferreteria_api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseInitializer {

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-config.json");
            if (serviceAccount == null) {
                throw new RuntimeException("firebase-config.json no encontrado en resources!");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println(" Firebase inicializado correctamente.");
        }
        } catch (IOException e) {
            throw new RuntimeException(" Error al inicializar Firebase: " + e.getMessage(), e);
        }
    }
}
