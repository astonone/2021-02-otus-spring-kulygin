package ru.otus.kulygin.candidateservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewerVO implements Serializable {

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
    private String secretKey;
    private boolean create;

}
