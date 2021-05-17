package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.InterviewTemplate;
import ru.otus.kulygin.repository.InterviewRepository;

@Component
public class CascadeInterviewTemplateOperationsMongoEventListener extends AbstractMongoEventListener<InterviewTemplate> {

    private final InterviewRepository interviewRepository;

    public CascadeInterviewTemplateOperationsMongoEventListener(InterviewRepository interviewRepository) {
        this.interviewRepository = interviewRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<InterviewTemplate> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewRepository.existsByInterviewTemplate_Id(id)) {
            throw new RuntimeException("Template has related interview");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<InterviewTemplate> event) {
        val template = event.getSource();
        val interviews = interviewRepository.findAllByInterviewTemplate_Id(template.getId());
        interviews.forEach(interview -> interview.setInterviewTemplate(template));
        interviewRepository.saveAll(interviews);
    }

}
