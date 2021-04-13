import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {InterviewerPageableDto} from "../models/pageable/Interviewer-pageable-dto";
import {Observable} from "rxjs";


@Injectable({
    providedIn: 'root'
})
export class InterviewersService {

    private readonly SERVICE: string;
    private GET_ALL: string;

    constructor(private sharedService: SharedService,
                private http: HttpClient) {

        this.SERVICE = this.sharedService.getServerURL() + '/api/interviewer/';
        this.GET_ALL = this.SERVICE + 'get-all?page={page}&pageSize={pageSize}';
    }

    public getAll(page: number, pageSize: number): Observable<InterviewerPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewerPageableDto>(url);
    }

}