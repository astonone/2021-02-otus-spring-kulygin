import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {InterviewService} from "../../services/interview-service";
import {InterviewDto} from "../../models/interview-dto";
import {CandidateDto} from "../../models/candidate-dto";
import {InterviewerDto} from "../../models/Interviewer-dto";
import {TemplateDto} from "../../models/template-dto";
import {InterviewTemplateCriteriaDto} from "../../models/interview-template-criteria-dto";
import {SharedService} from "../../services/shared-service";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'interview',
    templateUrl: './interview.component.html',
    styleUrls: [
        './interview.component.css'
    ]
})

export class InterviewComponent implements OnInit {

    public decisions: string[] = ["SHOULD_BE_HIRED", "SHOULD_NOT_BE_HIRED"];
    public isViewMode: boolean = false;
    public readonly interviewStatusProgress = 'IN_PROGRESS';
    private readonly interviewStatusPlanned = 'PLANNED';
    private readonly interviewStatusFinished = 'FINISHED';

    public currentInterviewId: string;
    public currentInterview: InterviewDto = new InterviewDto(null,
        new CandidateDto(null, null, null, null, null, null),
        new InterviewerDto(null, null, null, null, null, null, null, null), null,
        new TemplateDto(null, null, null), null, null, null, null, null);

    constructor(private route: ActivatedRoute,
                private interviewService: InterviewService,
                private sharedService: SharedService,
                private translateService: TranslateService) {
    }


    ngOnInit(): void {
        this.currentInterviewId = this.route.snapshot.paramMap.get('id');
        this.interviewService.getById(this.currentInterviewId).subscribe(data => {
            if (data.interviewStatus === this.interviewStatusPlanned) {
                data.interviewStatus = this.interviewStatusProgress;
                this.currentInterview = data;
                this.interviewService.save(this.currentInterview).subscribe(data => {
                });
            }
            if (data.interviewStatus === this.interviewStatusProgress) {
                this.currentInterview = data;
            }
            if (data.interviewStatus === this.interviewStatusFinished) {
                this.currentInterview = data;
                this.isViewMode = true;
            }
        }, error => {
            if (error.status === 403) {
                this.translateService.get('snackbar.rights').subscribe(t => {
                    this.sharedService.openSnackBar(t)
                });
            } else {
                this.sharedService.openSnackBar(error.error.message);
            }
        });
    }

    public onClickDownloadPdf(element: CandidateDto) {
        let base64String = element.cvFile;
        let fileName = element.firstName + element.lastName;
        this.downloadPdf(base64String, fileName);
    }

    private downloadPdf(base64String, fileName) {
        const source = `data:application/pdf;base64,${base64String}`;
        const link = document.createElement("a");
        link.href = source;
        link.download = `${fileName}.pdf`
        link.click();
    }

    public onFocusOutEvent(event: any, criteria: InterviewTemplateCriteriaDto) {
        this.interviewService.updateCriteriaComment(this.currentInterviewId, criteria.id, event.target.value).subscribe(data => {
            criteria.interviewerComment = event.target.value;
        });
    }

    public getMark(event: any) {
        this.currentInterview.totalMark = event;
    }

    public getCriteriaMark($event: string, criteria: InterviewTemplateCriteriaDto) {
        criteria.mark = Number($event);
    }

    public save() {
        this.currentInterview.interviewStatus = this.interviewStatusFinished;
        this.interviewService.save(this.currentInterview).subscribe(data => {
            this.currentInterview = data;
            this.isViewMode = true;
        }, error => {
            if (error.status === 403) {
                this.translateService.get('snackbar.rights').subscribe(t => {
                    this.sharedService.openSnackBar(t)
                });
            } else {
                this.sharedService.openSnackBar(error.error.message);
            }
        });
    }


    public isReadyForFinish() {
        return this.currentInterview.decisionStatus === 'NOT_APPLICABLE';
    }
}