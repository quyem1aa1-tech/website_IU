package com.app.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String username;
    private String fullName;
    private String studentId;
    private String email;

}
