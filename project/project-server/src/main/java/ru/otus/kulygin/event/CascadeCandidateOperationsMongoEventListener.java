package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Candidate;
import ru.otus.kulygin.repository.InterviewRepository;

@Component
public class CascadeCandidateOperationsMongoEventListener extends AbstractMongoEventListener<Candidate> {

    private final InterviewRepository interviewRepository;

    public CascadeCandidateOperationsMongoEventListener(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Candidate> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewRepository.existsByCandidate_Id(id)) {
            throw new RuntimeException("Candidate has related interview");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Candidate> event) {
        val candidate = event.getSource();
        val interviews = interviewRepository.findAllByCandidate_Id(candidate.getId());
        interviews.forEach(interview -> interview.setCandidate(candidate));
        interviewRepository.saveAll(interviews);
    }

}
