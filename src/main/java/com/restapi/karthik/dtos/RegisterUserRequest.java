package com.restapi.karthik.dtos;

import com.restapi.karthik.validation.LowerCase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;




@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 255,message = "Name must be less than 255 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "email must be valid")
    @LowerCase(message = "email must be in lowercase")
    private String email;

    @NotBlank(message = "password should not be blank")
    @Size(min = 6,max = 25, message = "Password must be at least 6 to 25 characters")
    private String password;
}
