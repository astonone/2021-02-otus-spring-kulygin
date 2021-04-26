import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {CandidatePageableDto} from "../models/pageable/candidate-pageable-dto";
import {CandidateDto} from "../models/candidate-dto";


@Injectable({
    providedIn: 'root'
})
export class CandidateService {

    private readonly SERVICE: string;
    private GET_ALL: string;
    private readonly SAVE: string;
    private DELETE: string;

    constructor(private sharedService: SharedService,
                private http: HttpClient) {

        this.SERVICE = this.sharedService.getServerURL() + '/candidate/';
        this.GET_ALL = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
    }

    public getAll(page: number, pageSize: number): Observable<CandidatePageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<CandidatePageableDto>(url);
    }

    public save(finalData: FormData): Observable<CandidateDto> {
        return this.http.post<CandidateDto>(this.SAVE, finalData);
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url);
    }

}