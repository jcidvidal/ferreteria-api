package com.ferreteriapfeifer.ferreteria_api.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;

@Component
public class FirebaseInitializer {

    @PostConstruct
    public void initialize() {
        try {
            InputStream serviceAccount = getClass().getClassLoader()
                    .getResourceAsStream("cred/ferreteria-api-prod.json");

            if (serviceAccount == null) {
                throw new RuntimeException("No se encontró el archivo JSON en cred/");
            }

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase inicializado correctamente.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar Firebase: " + e.getMessage(), e);
        }
    }
}
