import {Component, OnInit, ViewChild} from '@angular/core';
import {InterviewerDto} from "../../models/Interviewer-dto";
import {InterviewersService} from "../../services/interviewers-service";
import {PageEvent} from "@angular/material/paginator";
import {MatTable} from "@angular/material/table";
import {MatSnackBar, MatSnackBarHorizontalPosition, MatSnackBarVerticalPosition} from "@angular/material/snack-bar";

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

    public displayedColumns: string[] = ['firstName', 'lastName', 'actions'];
    public dataSource: InterviewerDto[] = [];

    private newInterviewer: InterviewerDto = new InterviewerDto(null, null, null);

    @ViewChild('interviewerTable') interviewerTable: MatTable<any>;

    // Snackbar options
    private horizontalPosition: MatSnackBarHorizontalPosition = 'end';
    private verticalPosition: MatSnackBarVerticalPosition = 'top';

    constructor(private interviewerService: InterviewersService,
                private snackBar: MatSnackBar) {
    }

    ngOnInit(): void {
        this.loadInterviewers(this.page, this.pageSize);
    }

    private loadInterviewers(page: number, pageSize: number): void {
        this.interviewerService.getAll(page, pageSize).subscribe(data => {
            this.totalSize = data.totalSize;
            this.totalPageSize = data.totalPageSize;
            this.currentPageSize = data.currentPageSize;
            this.page = data.page;
            this.pageSize = data.pageSize;
            this.dataSource = data.interviewers;
        });
    }

    public updatePage($event: PageEvent): PageEvent {
        if (this.totalSize > this.currentPageSize) {
            this.loadInterviewers($event.pageIndex, $event.pageSize);
        }
        return $event;
    }

    public makeEdit(element: InterviewerDto): void {
        element.isEdit = true;
        this.newInterviewer = new InterviewerDto(element.id, element.firstName, element.lastName);
        this.interviewerTable.renderRows();
    }

    public remove(element: InterviewerDto): void {
        this.interviewerService.removeById(element.id).subscribe(() => {
            this.removeFromDataSourceById(element);
        }, error => {
            this.openSnackBar(error.error.message);
        })
    }

    private removeFromDataSourceById(element: InterviewerDto): void {
        let index = this.getElementIndexInDataSource(element);

        this.dataSource.splice(index, 1);
        this.interviewerTable.renderRows();
    }

    private openSnackBar(snackBarText: string): void {
        this.snackBar.open(snackBarText, 'End now', {
            duration: 2000,
            horizontalPosition: this.horizontalPosition,
            verticalPosition: this.verticalPosition,
        });
    }

    public isReadyToUpdate(element: InterviewerDto): boolean {
        return !InterviewersComponent.isBlank(element.firstName) &&
            !InterviewersComponent.isBlank(element.lastName);
    }

    private static isBlank(str: string): boolean {
        return (!str || /^\s*$/.test(str));
    }

    public cancelEdit(element: InterviewerDto): void {
        element.isEdit = false;
        if (!element.id) {
            this.dataSource.splice(this.dataSource.findIndex(e => e === element), 1);
        } else {
            element.firstName = this.newInterviewer.firstName;
            element.lastName = this.newInterviewer.lastName;
        }
    }

    public update(element: InterviewerDto): void {
        this.interviewerService.update(InterviewerDto.createNewObjectFromDto(element)).subscribe(data => {
            this.updateDataSource(element, data);
            element.isEdit = false;
            this.interviewerTable.renderRows();
        })
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

    public create(): void {
        let interviewer = new InterviewerDto(null, null, null);
        interviewer.isEdit = true;
        this.dataSource.push(interviewer);
        this.interviewerTable.renderRows();
    }

}