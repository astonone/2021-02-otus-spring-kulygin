package ru.otus.kulygin.interviewservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.kulygin.interviewservice.domain.Interview;
import ru.otus.kulygin.interviewservice.enumerations.InterviewStatus;

import java.util.List;

public interface InterviewRepository extends MongoRepository<Interview, String>, InterviewRepositoryCustom {

    @Query("{'interviewTemplate.criterias': {$elemMatch: {_id:?0}}}")
    List<Interview> findAllByCriteriaId(String criteriaId);

    Page<Interview> findAllByInterviewStatus(InterviewStatus interviewStatus, Pageable pageable);

    boolean existsByInterviewTemplate_Id(String templateId);

    List<Interview> findAllByInterviewTemplate_IdAndInterviewStatus(String templateId, InterviewStatus interviewStatus);

    boolean existsByCandidate_Id(String candidateId);

    List<Interview> findAllByCandidate_Id(String candidateId);

    boolean existsByInterviewer_Id(String interviewerId);

    List<Interview> findAllByInterviewer_Id(String interviewerId);

}
