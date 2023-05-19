package ru.otus.kulygin.candidateservice.event;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.candidateservice.domain.Candidate;
import ru.otus.kulygin.candidateservice.service.InterviewService;
import ru.otus.kulygin.candidateservice.service.impl.MappingService;
import ru.otus.kulygin.candidateservice.vo.CandidateVO;

@Component
@AllArgsConstructor
public class CascadeCandidateOperationsMongoEventListener extends AbstractMongoEventListener<Candidate> {

    private final InterviewService interviewService;
    private final MappingService mappingService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Candidate> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewService.existsByCandidateId(id)) {
            throw new RuntimeException("Candidate has related interview");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Candidate> event) {
        val candidate = event.getSource();
        val interviews = interviewService.findAllByCandidateId(candidate.getId());
        final CandidateVO mappedCandidate = mappingService.map(candidate, CandidateVO.class);
        interviews.forEach(interview -> interview.setCandidate(mappedCandidate));
        interviewService.saveAllInterviews(interviews);
    }

}
