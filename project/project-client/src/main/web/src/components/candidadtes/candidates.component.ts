import {Component, OnInit} from '@angular/core';
import {PageEvent} from "@angular/material/paginator";
import {CandidateDto} from "../../models/candidate-dto";
import {CandidateService} from "../../services/candidate-service";
import {SharedService} from "../../services/shared-service";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'candidates',
    templateUrl: './candidates.component.html',
    styleUrls: [
        './candidates.component.css'
    ]
})

export class CandidatesComponent implements OnInit {

    public totalSize: number = 0;
    public totalPageSize: number = 0;
    public currentPageSize: number = 0;
    public page: number = 0;
    public pageSize: number = 10;
    public pageSizeOptions: number[] = [5, 10, 25, 100];

    public files: any;

    // MatPaginator Output
    public pageEvent: PageEvent;

    public dataSource: CandidateDto[] = [];

    public newCandidate: CandidateDto = new CandidateDto(null, null, null, null, null, null);
    public backupCandidate: CandidateDto = new CandidateDto(null, null, null, null, null, null);

    constructor(private candidateService: CandidateService,
                private sharedService: SharedService,
                private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.loadCandidates(this.page, this.pageSize);
    }

    private loadCandidates(page: number, pageSize: number): void {
        this.candidateService.getAllPageable(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.dataSource = data.candidates;
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

    public updatePage($event: PageEvent): PageEvent {
        if (this.totalSize > this.currentPageSize) {
            this.loadCandidates($event.pageIndex, $event.pageSize);
        }
        return $event;
    }

    public makeEdit(element: CandidateDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.newCandidate = CandidateDto.createNewObjectFromDto(element);
        this.backupCandidate = CandidateDto.createNewObjectFromDto(element);
    }

    public remove(element: CandidateDto): void {
        this.candidateService.removeById(element.id).subscribe(() => {
            this.removeFromDataSourceById(element);
            if (element.id) {
                this.currentPageSize--;
                this.totalSize--;
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

    private removeFromDataSourceById(element: CandidateDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1);
    }

    public isReadyToUpdate(): boolean {
        return !SharedService.isBlank(this.newCandidate.firstName) &&
            !SharedService.isBlank(this.newCandidate.lastName) &&
            !SharedService.isBlank(this.newCandidate.claimingPosition);
    }

    public cancelEdit(element: CandidateDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.dataSource.splice(this.dataSource.findIndex(e => e === element), 1);
        } else {
            element.firstName = this.backupCandidate.firstName;
            element.lastName = this.backupCandidate.lastName;
            element.claimingPosition = this.backupCandidate.claimingPosition;
            element.interviewerComment = this.backupCandidate.interviewerComment;
            this.newCandidate = new CandidateDto(null, null, null, null, null, null);
            this.backupCandidate = new CandidateDto(null, null, null, null, null, null);
        }
    }

    public update(): void {
        let finalData = this.createFormData();

        this.candidateService.save(finalData).subscribe(data => {
            this.newCandidate = new CandidateDto(null, null, null, null, null, null);
            this.backupCandidate = new CandidateDto(null, null, null, null, null, null);
            this.files = null;
            this.loadCandidates(this.page, this.pageSize);
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

    private createFormData(): FormData {
        let formData = new FormData();

        const json = JSON.stringify(this.newCandidate.toObject());
        const blob = new Blob([json], {
            type: 'application/json'
        });

        if (this.files) {
            let files: FileList = this.files;
            formData.append('uploadedFile', files[0]);
            formData.append('candidateDto', blob);
        } else {
            formData.append('candidateDto', blob);
        }

        return formData;
    }

    private getElementIndexInDataSource(element: CandidateDto) {
        return this.dataSource.map(function (item) {
            return item.id
        }).indexOf(element.id)
    }

    public create(): void {
        this.cancelEditingOtherElements();
        let candidate = new CandidateDto(null, null, null, null, null, null);
        candidate.isEdit = true;
        candidate.isNew = true;
        this.dataSource.push(candidate);
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

    public selectFile(event: any) {
        this.files = null;
        let target = event.target || event.srcElement;
        this.files = target.files;
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

}