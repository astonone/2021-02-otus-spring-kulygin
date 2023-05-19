package ru.otus.kulygin.candidateservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Candidate {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String claimingPosition;
    private String interviewerComment;
    private String pathToCvFile;

}
