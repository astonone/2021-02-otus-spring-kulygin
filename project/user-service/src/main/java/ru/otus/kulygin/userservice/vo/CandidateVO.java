package ru.otus.kulygin.userservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateVO implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String claimingPosition;
    private String interviewerComment;
    private String pathToCvFile;
    private byte[] cvFile;

}
