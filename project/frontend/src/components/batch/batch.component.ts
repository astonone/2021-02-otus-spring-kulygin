import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {InterviewTemplateCriteriaService} from "../../services/interview-template-criteria-service";
import {SharedService} from "../../services/shared-service";

@Component({
    selector: 'batch',
    templateUrl: './batch.component.html',
    styleUrls: [
        './batch.component.css'
    ]
})

export class BatchComponent implements OnInit {

    public files: any = null;
    public lastJobInfo: string = '...';

    constructor(private interviewTemplateCriteriaService: InterviewTemplateCriteriaService,
                private translateService: TranslateService,
                private sharedService: SharedService) {
    }

    ngOnInit(): void {
        this.loadLastJobInfo();
    }

    public loadLastJobInfo(): void {
        this.interviewTemplateCriteriaService.getLastJobInfo().subscribe(data => {
            this.lastJobInfo = data.message;
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

    public selectFile(event: any) {
        this.files = null;
        let target = event.target || event.srcElement;
        this.files = target.files;
    }

    public upload(): void {
        let finalData = this.createFormData();

        this.interviewTemplateCriteriaService.import(finalData).subscribe(data => {
            this.files = null;
            this.lastJobInfo = data.message;
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

        let files: FileList = this.files;
        formData.append('uploadedFile', files[0]);

        return formData;
    }

}