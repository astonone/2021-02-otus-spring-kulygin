import {Injectable} from '@angular/core';
import {LocalStorageService} from "./local-storage-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CandidatePageableDto} from "../models/pageable/candidate-pageable-dto";
import {CandidateDto} from "../models/candidate-dto";
import {UserService} from "./user.service";


@Injectable({
    providedIn: 'root'
})
export class CandidateService {

    private readonly SERVICE: string;
    private readonly GET_ALL: string;
    private readonly SAVE: string;
    private GET_ALL_PAGEABLE: string;
    private DELETE: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {

        this.SERVICE = this.localStorageService.getServerURL() + '/candidate-service/candidate/';
        this.GET_ALL_PAGEABLE = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.GET_ALL = this.SERVICE;
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
    }

    public getAllPageable(page: number, pageSize: number): Observable<CandidatePageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL_PAGEABLE.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<CandidatePageableDto>(url, this.userService.getOptions());
    }

    public getAll(): Observable<CandidatePageableDto> {
        return this.http.get<CandidatePageableDto>(this.GET_ALL, this.userService.getOptions());
    }

    public save(finalData: FormData): Observable<CandidateDto> {
        return this.http.post<CandidateDto>(this.SAVE, finalData, this.userService.getOptions());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url, this.userService.getOptions());
    }

}