package com.example.stocktkl.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "username is mandatory")
    @Size(min = 3, max = 20)
    private String username;

    @NotNull(message = "password is mandatory")
    @Size(min = 6, max = 40)
    private String password;
}
