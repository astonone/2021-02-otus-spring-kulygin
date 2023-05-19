package ru.otus.kulygin.templateservice.event;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.templateservice.domain.InterviewTemplate;
import ru.otus.kulygin.templateservice.service.InterviewService;
import ru.otus.kulygin.templateservice.service.impl.MappingService;
import ru.otus.kulygin.templateservice.vo.InterviewTemplateVO;

@Component
@AllArgsConstructor
public class CascadeInterviewTemplateOperationsMongoEventListener extends AbstractMongoEventListener<InterviewTemplate> {

    private final InterviewService interviewService;
    private final MappingService mappingService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<InterviewTemplate> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewService.existByTemplateId(id)) {
            throw new RuntimeException("Template has related interview");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<InterviewTemplate> event) {
        val template = event.getSource();
        val interviews = interviewService.findAllByTemplateId(template.getId());
        final InterviewTemplateVO mappedTemplate = mappingService.map(template, InterviewTemplateVO.class);
        interviews.forEach(interview -> interview.setInterviewTemplate(mappedTemplate));
        interviewService.saveAll(interviews);
    }
}
