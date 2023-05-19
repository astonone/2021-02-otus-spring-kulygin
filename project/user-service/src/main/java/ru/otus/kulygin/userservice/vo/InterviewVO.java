package ru.otus.kulygin.userservice.vo;

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
public class InterviewVO implements Serializable {

    private String id;
    private CandidateVO candidate;
    private InterviewerVO interviewer;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime interviewDateTime;
    private InterviewTemplateVO interviewTemplate;
    private String interviewStatus;
    private Double totalMark;
    private String totalComment;
    private String desiredSalary;
    private String decisionStatus;

}
