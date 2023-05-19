package ru.otus.kulygin.interviewservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.otus.kulygin.interviewservice.enumerations.DecisionStatus;
import ru.otus.kulygin.interviewservice.enumerations.InterviewStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Interview {

    @Id
    private String id;
    private Candidate candidate;
    private Interviewer interviewer;
    private LocalDateTime interviewDateTime;
    private InterviewTemplate interviewTemplate;
    private InterviewStatus interviewStatus;
    private Double totalMark;
    private String totalComment;
    private String desiredSalary;
    private DecisionStatus decisionStatus;

}
