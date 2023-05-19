package ru.otus.kulygin.interviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    private String id;
    private String firstName;
    private String lastName;
    private String claimingPosition;
    private String interviewerComment;
    private String pathToCvFile;

}
