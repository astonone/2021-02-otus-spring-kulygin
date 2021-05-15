import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InterviewPageableDto} from "../models/pageable/interview-pageable-dto";
import {InterviewDto} from "../models/interview-dto";


@Injectable({
    providedIn: 'root'
})
export class InterviewService {

    private readonly SERVICE: string;
    private GET_ALL: string;
    private readonly SAVE: string;
    private DELETE: string;

    constructor(private sharedService: SharedService,
                private http: HttpClient) {

        this.SERVICE = this.sharedService.getServerURL() + '/interview/';
        this.GET_ALL = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
    }

    public getAll(page: number, pageSize: number): Observable<InterviewPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewPageableDto>(url);
    }

    public save(interview: InterviewDto): Observable<InterviewDto> {
        return this.http.post<InterviewDto>(this.SAVE, interview.toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url);
    }

}