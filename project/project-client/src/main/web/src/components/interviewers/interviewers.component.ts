import {Component, OnInit} from '@angular/core';
import {InterviewerDto} from "../../models/Interviewer-dto";
import {InterviewersService} from "../../services/interviewers-service";
import {PageEvent} from "@angular/material/paginator";

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

    constructor(private interviewerService: InterviewersService) {
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

}