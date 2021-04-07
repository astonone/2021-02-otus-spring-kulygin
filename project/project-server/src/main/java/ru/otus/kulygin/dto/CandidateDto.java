package ru.otus.kulygin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDto {

    private String id;
    private String firstName;
    private String lastName;
    private String claimingPosition;
    private String interviewerComment;
    private String pathToCvFile;

}
