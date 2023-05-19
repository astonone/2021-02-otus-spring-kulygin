package ru.otus.kulygin.userservice.event;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.userservice.domain.Interviewer;
import ru.otus.kulygin.userservice.service.InterviewService;
import ru.otus.kulygin.userservice.service.impl.MappingService;
import ru.otus.kulygin.userservice.vo.InterviewerVO;

@Component
@AllArgsConstructor
public class CascadeInterviewerOperationsMongoEventListener extends AbstractMongoEventListener<Interviewer> {

    private final InterviewService interviewService;
    private final MappingService mappingService;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Interviewer> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (interviewService.existsByInterviewerId(id)) {
            throw new RuntimeException("Interviewer has related interview");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Interviewer> event) {
        val interviewer = event.getSource();
        val interviews = interviewService.findAllByInterviewerId(interviewer.getId());
        final InterviewerVO mappedInterviewer = mappingService.map(interviewer, InterviewerVO.class);
        interviews.forEach(interview -> interview.setInterviewer(mappedInterviewer));
        interviewService.saveAllInterviews(interviews);
    }

}
