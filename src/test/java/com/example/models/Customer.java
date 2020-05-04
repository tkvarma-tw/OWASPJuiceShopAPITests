package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String email;
    private String password;
    private String passwordRepeat;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;
}
