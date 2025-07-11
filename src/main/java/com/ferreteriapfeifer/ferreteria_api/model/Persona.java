package com.ferreteriapfeifer.ferreteria_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import com.google.firebase.database.annotations.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IgnoreExtraProperties
@Schema(description = "Clase base para usuarios del sistema")
public abstract class Persona {

    @NotNull @Size(min = 2, max = 50)
    @Schema(description = "Nombre completo de la persona", example = "Julio Martinez")
    private String nombre;


    @Email
    @Schema(description = "correo electronico valido", example = "julio.martinez@email.com")
    private String email;
    @Pattern(regexp = "\\+\\d{11}", message = "El teléfono debe tener formato +56912345678")
    @Schema(description = "Teléfono en formato internacional", example = "+56912345678")
    private String telefono;


    @Schema(description = "Contraseña del usuario (mínimo 6 caracteres)", example = "segura123")
    private String contrasena;


    @Schema(description = "Rol del usuario", example = "ADMIN")
    private String rol;
}

