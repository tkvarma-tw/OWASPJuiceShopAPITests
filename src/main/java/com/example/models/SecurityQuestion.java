package com.example.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityQuestion {
    private int id;
    private String question;
    private String createdAt;
    private String updatedAt;
}
