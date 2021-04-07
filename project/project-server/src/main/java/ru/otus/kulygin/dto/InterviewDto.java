package ru.otus.kulygin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.kulygin.domain.Candidate;
import ru.otus.kulygin.domain.InterviewTemplate;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.enumeration.DecisionStatus;
import ru.otus.kulygin.enumeration.InterviewStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDto {

    private String id;
    private Candidate candidate;
    private Interviewer interviewer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime interviewDateTime;
    private InterviewTemplate interviewTemplate;
    private InterviewStatus interviewStatus;
    private Double totalMark;
    private String totalComment;
    private String desiredSalary;
    private DecisionStatus decisionStatus;

}
