import {Injectable} from '@angular/core';
import {LocalStorageService} from "./local-storage-service";
import {HttpClient} from "@angular/common/http";
import {InterviewerPageableDto} from "../models/pageable/Interviewer-pageable-dto";
import {Observable} from "rxjs";
import {InterviewerDto} from "../models/Interviewer-dto";
import {UserService} from "./user.service";


@Injectable({
    providedIn: 'root'
})
export class InterviewersService {

    private readonly SERVICE: string;
    private readonly GET_ALL: string;
    private readonly SAVE: string;
    private GET_ALL_PAGEABLE: string;
    private DELETE: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {

        this.SERVICE = this.localStorageService.getServerURL() + '/user-service/interviewer/';
        this.GET_ALL_PAGEABLE = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.GET_ALL = this.SERVICE;
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
    }

    public getAllPageable(page: number, pageSize: number): Observable<InterviewerPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL_PAGEABLE.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewerPageableDto>(url, this.userService.getOptions());
    }

    public getAll(): Observable<InterviewerPageableDto> {
        return this.http.get<InterviewerPageableDto>(this.GET_ALL, this.userService.getOptions());
    }

    public save(interviewer: InterviewerDto): Observable<InterviewerDto> {
        return this.http.post<InterviewerDto>(this.SAVE, interviewer.toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url, this.userService.getOptions());
    }

}