import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InterviewTemplateCriteriaPageableDto} from "../models/pageable/interview-template-criteria-pageable-dto";
import {InterviewTemplateCriteriaDto} from "../models/interview-template-criteria-dto";

@Injectable({
    providedIn: 'root'
})
export class InterviewTemplateCriteriaService {

    private readonly SERVICE: string;
    private GET_ALL: string;
    private readonly SAVE: string;
    private DELETE: string;

    constructor(private sharedService: SharedService,
                private http: HttpClient) {

        this.SERVICE = this.sharedService.getServerURL() + '/interview-template-criteria/';
        this.GET_ALL = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
    }

    public getAll(page: number, pageSize: number): Observable<InterviewTemplateCriteriaPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewTemplateCriteriaPageableDto>(url);
    }

    public save(criteria: InterviewTemplateCriteriaDto): Observable<InterviewTemplateCriteriaDto> {
        return this.http.post<InterviewTemplateCriteriaDto>(this.SAVE, criteria.toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url);
    }

}