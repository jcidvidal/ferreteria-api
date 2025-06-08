package com.ferreteriapfeifer.ferreteria_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class FerreteriaApiApplication {

    public static void main(String[] args) throws IOException {
        System.out.println("Ferreteria API Application, v1.0.0");

        SpringApplication.run(FerreteriaApiApplication.class, args);
    }

}
