package ru.otus.kulygin.interviewservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDto implements Serializable {

    private String id;
    private CandidateDto candidate;
    private InterviewerDto interviewer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime interviewDateTime;
    private InterviewTemplateDto interviewTemplate;
    private String interviewStatus;
    private Double totalMark;
    private String totalComment;
    private String desiredSalary;
    private String decisionStatus;

}
