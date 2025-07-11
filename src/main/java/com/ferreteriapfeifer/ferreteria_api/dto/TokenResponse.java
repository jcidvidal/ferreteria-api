package com.ferreteriapfeifer.ferreteria_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    private String token;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String rol;
}
