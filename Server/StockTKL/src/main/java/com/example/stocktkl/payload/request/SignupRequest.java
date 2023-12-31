package com.example.stocktkl.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotNull(message = "username is mandatory")
    @Size(min = 3, max = 20)
    private String username;

    @NotNull(message = "Email is mandatory")
    @Size(max = 50)
    @Email
    private String email;

    @NotNull(message = "Full Name is mandatory")
    @Size(max =100 )
    private String fullName;


    @NotNull(message = "password is mandatory")
    @Size(min = 6, max = 40)
    private String password;
}
