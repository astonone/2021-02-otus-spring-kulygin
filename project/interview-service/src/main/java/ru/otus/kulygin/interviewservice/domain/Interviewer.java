package ru.otus.kulygin.interviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interviewer {

    private String id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String positionType;
    private boolean enabled;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private String role;

}
