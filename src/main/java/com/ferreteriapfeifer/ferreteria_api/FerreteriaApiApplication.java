package com.ferreteriapfeifer.ferreteria_api;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class FerreteriaApiApplication {

	public static void main(String[] args) throws IOException {
		System.out.println("Ferreteria API Application, v1.0.0");
		InputStream serviceAccount = FerreteriaApiApplication.class.getClassLoader()
				.getResourceAsStream("./cred/ferreteria-api-prod.json");

		if (serviceAccount == null) {
			throw new FileNotFoundException("No se encontró el archivo JSON en cred/");
		}
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}



		SpringApplication.run(FerreteriaApiApplication.class, args);
	}

}
