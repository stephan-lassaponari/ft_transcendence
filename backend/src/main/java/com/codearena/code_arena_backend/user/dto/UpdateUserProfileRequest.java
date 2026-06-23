package com.codearena.code_arena_backend.user.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserProfileRequest {

    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @Size(max = 100, message = "displayName must be at most 100 characters")
    private String displayName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 2000, message = "bio must be at most 2000 characters")
    private String bio;

    public UpdateUserProfileRequest(String displayName, String email, String bio) {
        this.displayName = displayName;
        this.email = email;
        this.bio = bio;
    }

    public UpdateUserProfileRequest(String username, String displayName, String email, String bio) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.bio = bio;
    }
}
