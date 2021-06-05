import {Component, OnInit} from '@angular/core';
import {InterviewerDto} from "../../models/Interviewer-dto";
import {InterviewersService} from "../../services/interviewers-service";
import {PageEvent} from "@angular/material/paginator";
import {SharedService} from "../../services/shared-service";
import {UserService} from "../../services/user.service";
import {TranslateService} from "@ngx-translate/core";

@Component({
    selector: 'interviewers',
    templateUrl: './interviewers.component.html',
    styleUrls: [
        './interviewers.component.css'
    ]
})

export class InterviewersComponent implements OnInit {

    public totalSize: number = 0;
    public totalPageSize: number = 0;
    public currentPageSize: number = 0;
    public page: number = 0;
    public pageSize: number = 10;
    public pageSizeOptions: number[] = [5, 10, 25, 100];

    // MatPaginator Output
    public pageEvent: PageEvent;

    public dataSource: InterviewerDto[] = [];

    public newInterviewer: InterviewerDto = new InterviewerDto(null, null, null, null, null, null, null, null);
    public backupInterviewer: InterviewerDto = new InterviewerDto(null, null, null, null, null, null, null, null);

    constructor(private interviewerService: InterviewersService,
                private userService: UserService,
                private sharedService: SharedService,
                private translateService: TranslateService) {
    }

    ngOnInit(): void {
        this.loadInterviewers(this.page, this.pageSize);
    }

    private loadInterviewers(page: number, pageSize: number): void {
        this.interviewerService.getAllPageable(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.dataSource = data.interviewers;
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
            this.loadInterviewers($event.pageIndex, $event.pageSize);
        }
        return $event;
    }

    public makeEdit(element: InterviewerDto): void {
        this.cancelEditingOtherElements();
        element.isEdit = true;
        this.newInterviewer = InterviewerDto.createNewObjectFromDto(element);
        this.backupInterviewer = InterviewerDto.createNewObjectFromDto(element);
    }

    public remove(element: InterviewerDto): void {
        this.interviewerService.removeById(element.id).subscribe(() => {
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

    private removeFromDataSourceById(element: InterviewerDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1);
    }

    public isReadyToUpdate(): boolean {
        return !SharedService.isBlank(this.newInterviewer.firstName) &&
            !SharedService.isBlank(this.newInterviewer.lastName) &&
            !SharedService.isBlank(this.newInterviewer.positionType);
    }

    public cancelEdit(element: InterviewerDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.dataSource.splice(this.dataSource.findIndex(e => e === element), 1);
        } else {
            element.firstName = this.backupInterviewer.firstName;
            element.lastName = this.backupInterviewer.lastName;
            element.positionType = this.backupInterviewer.positionType;
            element.username = this.backupInterviewer.username;
            this.newInterviewer = new InterviewerDto(null, null, null, null, null, null, null, null);
            this.backupInterviewer = new InterviewerDto(null, null, null, null, null, null, null, null);
        }
    }

    public update(element: InterviewerDto): void {
        this.interviewerService.save(this.newInterviewer).subscribe(data => {
            let loggedUser = this.userService.getLoggedUser();
            if (loggedUser.id === data.id) {
                loggedUser.firstName = data.firstName;
                loggedUser.lastName = data.lastName;
                loggedUser.positionType = data.positionType;
                this.userService.setLoggedUser(loggedUser);
            }
            this.newInterviewer = new InterviewerDto(null, null, null, null, null, null, null, null);
            this.backupInterviewer = new InterviewerDto(null, null, null, null, null, null, null, null);
            if (this.dataSource.length + 1 <= this.currentPageSize) {
                this.updateDataSource(element, data);
                element.isEdit = false;
                this.totalSize++;
                this.currentPageSize++;
            } else {
                this.loadInterviewers(this.page, this.pageSize);
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

    private updateDataSource(element: any, interviewer: InterviewerDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1, interviewer);
    }

    private getElementIndexInDataSource(element: InterviewerDto) {
        return this.dataSource.map(function (item) {
            return item.id
        }).indexOf(element.id)
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

}