import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";
import {SharedService} from "../../services/shared.service";
import {InterviewDto} from "../../models/interview-dto";
import {InterviewService} from "../../services/interview-service";
import {CandidateDto} from "../../models/candidate-dto";
import {InterviewerDto} from "../../models/Interviewer-dto";
import {InterviewersService} from "../../services/interviewers-service";
import {CandidateService} from "../../services/candidate-service";
import {TemplateDto} from "../../models/template-dto";
import {TemplateService} from "../../services/template-service";

@Component({
    selector: 'interviews',
    templateUrl: './interviews.component.html',
    styleUrls: [
        './interviews.component.css'
    ]
})

export class InterviewsComponent implements OnInit {

    public totalSize: number = 0;
    public totalPageSize: number = 0;
    public currentPageSize: number = 0;
    public page: number = 0;
    public pageSize: number = 10;
    public pageSizeOptions: number[] = [5, 10, 25, 100];

    // MatPaginator Output
    public pageEvent: PageEvent;

    public dataSource: InterviewDto[] = [];
    public interviewers: InterviewerDto[] = [];
    public candidates: CandidateDto[] = [];
    public templates: TemplateDto[] = [];
    public statuses: string[] = ["PLANNED", "IN_PROGRESS", "FINISHED"];
    public decisions: string[] = ["NOT_APPLICABLE", "SHOULD_BE_HIRED", "SHOULD_NOT_BE_HIRED"];

    public newInterviewer: InterviewDto = new InterviewDto(null,
        new CandidateDto(null, null, null, null, null, null),
        new InterviewerDto(null, null, null, null), null,
        new TemplateDto(null, null, null), null, null, null, null, null);
    public backupInterviewer: InterviewDto = new InterviewDto(null,
        new CandidateDto(null, null, null, null, null, null),
        new InterviewerDto(null, null, null, null), null,
        new TemplateDto(null, null, null), null, null, null, null, null);

    // Snackbar options
    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';

    constructor(private interviewService: InterviewService,
                private interviewersService: InterviewersService,
                private candidateService: CandidateService,
                private templateService: TemplateService,
                private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.loadInterviews(this.page, this.pageSize);
        this.loadInterviewers();
        this.loadCandidates();
        this.loadTemplates();
    }

    private loadInterviews(page: number, pageSize: number): void {
        this.interviewService.getAll(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.dataSource = data.interviews;
        });
    }

    private loadInterviewers(): void {
        this.interviewersService.getAll().subscribe(data => {
            this.interviewers = data.interviewers;
        });
    }

    private loadCandidates(): void {
        this.candidateService.getAll().subscribe(data => {
            this.candidates = data.candidates;
        });
    }

    private loadTemplates(): void {
        this.templateService.getAll().subscribe(data => {
            this.templates = data.templates;
        });
    }

    public updatePage($event: PageEvent): PageEvent {
        if (this.totalSize > this.currentPageSize) {
            this.loadInterviews($event.pageIndex, $event.pageSize);
        }
        return $event;
    }

    public makeEdit(element: InterviewDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.newInterviewer = InterviewDto.createNewObjectFromDto(element);
        this.newInterviewer.interviewDate = InterviewDto.getDate(this.newInterviewer.interviewDateTime);
        this.newInterviewer.interviewTime = InterviewDto.getTime(this.newInterviewer.interviewDateTime);
        this.backupInterviewer = InterviewDto.createNewObjectFromDto(element);
        this.backupInterviewer.interviewDate = InterviewDto.getDate(this.backupInterviewer.interviewDateTime);
        this.backupInterviewer.interviewTime = InterviewDto.getTime(this.backupInterviewer.interviewDateTime);
    }

    public remove(element: InterviewDto): void {
        this.interviewService.removeById(element.id).subscribe(() => {
            this.removeFromDataSourceById(element);
            if (element.id) {
                this.currentPageSize--;
                this.totalSize--;
            }
        }, error => {
            this.openSnackBar(error.error.message);
        })
    }

    private removeFromDataSourceById(element: InterviewDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1);
    }

    private openSnackBar(snackBarText: string): void {
        this.snackBar.open(snackBarText, 'End now', {
            duration: 2000,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

    public isReadyToUpdate(): boolean {
        return this.newInterviewer.candidate.firstName !== null && this.newInterviewer.interviewer.firstName !== null &&
            this.newInterviewer.interviewTemplate.positionName !== null;
    }

    public cancelEdit(element: InterviewDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.dataSource.splice(this.dataSource.findIndex(e => e === element), 1);
        } else {
            element.candidate = this.backupInterviewer.candidate;
            element.interviewer = this.backupInterviewer.interviewer;
            element.interviewTemplate = this.backupInterviewer.interviewTemplate;
            element.interviewDateTime = this.backupInterviewer.interviewDateTime;
            element.interviewStatus = this.backupInterviewer.interviewStatus;
            element.desiredSalary = this.backupInterviewer.desiredSalary;
            element.decisionStatus = this.backupInterviewer.decisionStatus;
            element.interviewStatus = this.backupInterviewer.interviewStatus;
            element.totalComment = this.backupInterviewer.totalComment;
            this.newInterviewer = new InterviewDto(null,
                new CandidateDto(null, null, null, null, null, null),
                new InterviewerDto(null, null, null, null), null,
                new TemplateDto(null, null, null), null, null, null, null, null);
            this.backupInterviewer = new InterviewDto(null,
                new CandidateDto(null, null, null, null, null, null),
                new InterviewerDto(null, null, null, null), null,
                new TemplateDto(null, null, null), null, null, null, null, null);
        }
    }

    public update(element: InterviewDto): void {
        this.newInterviewer.buildDateAndTime();
        this.interviewService.save(this.newInterviewer).subscribe(data => {
            this.newInterviewer = new InterviewDto(null, null, null, null, null, null, null, null, null, null);
            this.backupInterviewer = new InterviewDto(null, null, null, null, null, null, null, null, null, null);
            if (this.dataSource.length + 1 <= this.currentPageSize) {
                this.updateDataSource(element, data);
                element.isEdit = false;
                this.totalSize++;
                this.currentPageSize++;
            } else {
                this.loadInterviews(this.page, this.pageSize);
            }
        })
    }

    private updateDataSource(element: any, interviewer: InterviewDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1, interviewer);
    }

    private getElementIndexInDataSource(element: InterviewDto) {
        return this.dataSource.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public create(): void {
        this.cancelEditingOtherElements();
        let interviewer = new InterviewDto(null,
            new CandidateDto(null, null, null, null, null, null),
            new InterviewerDto(null, null, null, null), null,
            new TemplateDto(null, null, null), null, null, null, null, null);
        interviewer.isEdit = true;
        interviewer.isNew = true;
        this.dataSource.push(interviewer);
    }

    private cancelEditingOtherElements(): void {
        function isEditing(element, index, array) {
            return (element.isEdit);
        }

        let filtered = this.dataSource.filter(isEditing);
        if (filtered.length > 0) {
            this.cancelEdit(filtered[0]);
        }
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

    public objectComparisonFunction = function (option: any, value: any): boolean {
        return option.id === value.id;
    }

}