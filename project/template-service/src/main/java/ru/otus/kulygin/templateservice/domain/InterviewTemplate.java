package ru.otus.kulygin.templateservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class InterviewTemplate implements Serializable {

    @Id
    private String id;
    private String positionName;
    private List<InterviewTemplateCriteria> criterias;

}
