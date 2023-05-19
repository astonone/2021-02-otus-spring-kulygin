import {Injectable} from '@angular/core';
import {LocalStorageService} from "./local-storage-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InterviewTemplateCriteriaPageableDto} from "../models/pageable/interview-template-criteria-pageable-dto";
import {InterviewTemplateCriteriaDto} from "../models/interview-template-criteria-dto";
import {UserService} from "./user.service";
import {BatchInfoDto} from '../models/batch-info-dto';

@Injectable({
    providedIn: 'root'
})
export class InterviewTemplateCriteriaService {

    private readonly SERVICE: string;
    private readonly SAVE: string;
    private GET_ALL: string;
    private DELETE: string;
    private IMPORT: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {

        this.SERVICE = this.localStorageService.getServerURL() + '/criteria-service/interview-template-criteria/';
        this.GET_ALL = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
        this.IMPORT = this.SERVICE + 'job/import-criterias';
    }

    public getAll(page: number, pageSize: number): Observable<InterviewTemplateCriteriaPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewTemplateCriteriaPageableDto>(url, this.userService.getOptions());
    }

    public save(criteria: InterviewTemplateCriteriaDto): Observable<InterviewTemplateCriteriaDto> {
        return this.http.post<InterviewTemplateCriteriaDto>(this.SAVE, criteria.toObject(), this.userService.getOptions());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url, this.userService.getOptions());
    }

    public import(finalData: FormData): Observable<BatchInfoDto> {
        return this.http.post<BatchInfoDto>(this.IMPORT, finalData, this.userService.getOptions());
    }

    public getLastJobInfo(): Observable<BatchInfoDto> {
        return this.http.get<BatchInfoDto>(this.IMPORT, this.userService.getOptions());
    }

}