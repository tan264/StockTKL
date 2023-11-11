package com.example.stocktkl.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "username is mandatory")
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank(message = "password is mandatory")
    @Size(min = 6, max = 40)
    private String password;
}
