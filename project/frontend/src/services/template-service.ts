import {Injectable} from '@angular/core';
import {LocalStorageService} from "./local-storage-service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TemplatePageableDto} from "../models/pageable/template-pageable-dto";
import {TemplateDto} from "../models/template-dto";
import {InterviewTemplateCriteriaDto} from "../models/interview-template-criteria-dto";
import {UserService} from "./user.service";


@Injectable({
    providedIn: 'root'
})
export class TemplateService {

    private readonly SERVICE: string;
    private readonly GET_ALL: string;
    private readonly SAVE: string;
    private GET_ALL_PAGEABLE: string;
    private DELETE: string;
    private GET_BY_ID: string;
    private ADD_CRITERIA: string;
    private REMOVE_CRITERIA: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {

        this.SERVICE = this.localStorageService.getServerURL() + '/template-service/interview-template/';
        this.GET_ALL_PAGEABLE = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.GET_ALL = this.SERVICE;
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
        this.GET_BY_ID = this.SERVICE + '{id}';
        this.ADD_CRITERIA = this.SERVICE + '{templateId}/criteria';
        this.REMOVE_CRITERIA = this.SERVICE + '{templateId}/criteria/{criteriaId}';
    }

    public getAllPageable(page: number, pageSize: number): Observable<TemplatePageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL_PAGEABLE.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<TemplatePageableDto>(url, this.userService.getOptions());
    }

    public getAll(): Observable<TemplatePageableDto> {
        return this.http.get<TemplatePageableDto>(this.GET_ALL, this.userService.getOptions());
    }

    public save(template: TemplateDto): Observable<TemplateDto> {
        return this.http.post<TemplateDto>(this.SAVE, template.toObject(), this.userService.getOptions());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url, this.userService.getOptions());
    }

    public getById(id: string): Observable<TemplateDto> {
        const regExpId = /{id}/gi;
        const url = this.GET_BY_ID.replace(regExpId, id);
        return this.http.get<TemplateDto>(url, this.userService.getOptions());
    }

    public addCriteria(templateId: string, criteria: InterviewTemplateCriteriaDto): Observable<TemplateDto> {
        const regExpId = /{templateId}/gi;
        const url = this.ADD_CRITERIA.replace(regExpId, templateId);
        return this.http.post<TemplateDto>(url, InterviewTemplateCriteriaDto.createNewObjectFromDto(criteria).toObject(), this.userService.getOptions());
    }

    public removeCriteria(templateId: string, criteriaId: string): Observable<TemplateDto> {
        const regExpId = /{templateId}/gi;
        const regExpId2 = /{criteriaId}/gi;
        let url = this.REMOVE_CRITERIA.replace(regExpId, templateId);
        url = url.replace(regExpId2, criteriaId);
        return this.http.delete<TemplateDto>(url, this.userService.getOptions());
    }

}