package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Interviewer;
import ru.otus.kulygin.repository.InterviewRepository;

@Component
public class CascadeInterviewerOperationsMongoEventListener extends AbstractMongoEventListener<Interviewer> {

    private final InterviewRepository interviewRepository;

    public CascadeInterviewerOperationsMongoEventListener(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Interviewer> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewRepository.existsByInterviewer_Id(id)) {
            throw new RuntimeException("Interviewer has related interview");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Interviewer> event) {
        val interviewer = event.getSource();
        val interviews = interviewRepository.findAllByInterviewer_Id(interviewer.getId());
        interviews.forEach(interview -> interview.setInterviewer(interviewer));
        interviewRepository.saveAll(interviews);
    }

}
