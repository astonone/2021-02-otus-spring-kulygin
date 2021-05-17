import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {InterviewPageableDto} from "../models/pageable/interview-pageable-dto";
import {InterviewDto} from "../models/interview-dto";
import {InterviewTemplateCriteriaDto} from "../models/interview-template-criteria-dto";


@Injectable({
    providedIn: 'root'
})
export class InterviewService {

    private readonly SERVICE: string;
    private GET_ALL: string;
    private GET_ALL_BY_STATUS: string;
    private GET_BY_ID: string;
    private UPDATE_CRITERIA: string;
    private UPDATE_CRITERIA_COMMENT: string;
    private readonly SAVE: string;
    private DELETE: string;

    constructor(private sharedService: SharedService,
                private http: HttpClient) {

        this.SERVICE = this.sharedService.getServerURL() + '/interview/';
        this.GET_ALL = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.GET_ALL_BY_STATUS = this.SERVICE + '/status/?status={status}&page={page}&pageSize={pageSize}';
        this.GET_BY_ID = this.SERVICE + '{id}';
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
        this.UPDATE_CRITERIA = this.SERVICE + '{interviewId}/criteria/{criteriaId}?mark={mark}';
        this.UPDATE_CRITERIA_COMMENT = this.SERVICE + '{interviewId}/criteria/{criteriaId}/comment';
    }

    public getAll(page: number, pageSize: number): Observable<InterviewPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewPageableDto>(url);
    }

    public getAllByStatus(status: string, page: number, pageSize: number): Observable<InterviewPageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const regExpStatus = /{status}/gi;
        const url = this.GET_ALL_BY_STATUS.replace(regExpStatus, status).replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<InterviewPageableDto>(url);
    }

    public save(interview: InterviewDto): Observable<InterviewDto> {
        return this.http.post<InterviewDto>(this.SAVE, InterviewDto.createNewObjectFromDto(interview).toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url);
    }

    public getById(id: string): Observable<InterviewDto> {
        const regExpId = /{id}/gi;
        const url = this.GET_BY_ID.replace(regExpId, id);
        return this.http.get<InterviewDto>(url);
    }

    public updateCriteria(interviewId: string, criteriaId: string, mark: number): Observable<InterviewDto> {
        const regExpInterviewId = /{interviewId}/gi;
        const regExpPageCriteriaId = /{criteriaId}/gi;
        const regExpMark = /{mark}/gi;
        const url = this.UPDATE_CRITERIA.replace(regExpInterviewId, interviewId).replace(regExpPageCriteriaId, criteriaId).replace(regExpMark, mark.toString());
        return this.http.post<InterviewDto>(url, null);
    }

    public updateCriteriaComment(interviewId: string, criteriaId: string, comment: string): Observable<InterviewDto> {
        const regExpInterviewId = /{interviewId}/gi;
        const regExpPageCriteriaId = /{criteriaId}/gi;
        const url = this.UPDATE_CRITERIA_COMMENT.replace(regExpInterviewId, interviewId).replace(regExpPageCriteriaId, criteriaId);
        return this.http.post<InterviewDto>(url, new InterviewTemplateCriteriaDto(null, null, null, null, comment).toObject());
    }

}