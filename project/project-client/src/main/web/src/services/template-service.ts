import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TemplatePageableDto} from "../models/pageable/template-pageable-dto";
import {TemplateDto} from "../models/template-dto";


@Injectable({
    providedIn: 'root'
})
export class TemplateService {

    private readonly SERVICE: string;
    private GET_ALL: string;
    private readonly SAVE: string;
    private DELETE: string;

    constructor(private sharedService: SharedService,
                private http: HttpClient) {

        this.SERVICE = this.sharedService.getServerURL() + '/interview-template/';
        this.GET_ALL = this.SERVICE + '?page={page}&pageSize={pageSize}';
        this.SAVE = this.SERVICE;
        this.DELETE = this.SERVICE + '{id}';
    }

    public getAll(page: number, pageSize: number): Observable<TemplatePageableDto> {
        const regExpPage = /{page}/gi;
        const regExpPageSize = /{pageSize}/gi;
        const url = this.GET_ALL.replace(regExpPage, page.toString()).replace(regExpPageSize, pageSize.toString());
        return this.http.get<TemplatePageableDto>(url);
    }

    public save(template: TemplateDto): Observable<TemplateDto> {
        return this.http.post<TemplateDto>(this.SAVE, template.toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExpId = /{id}/gi;
        const url = this.DELETE.replace(regExpId, id);
        return this.http.delete<Observable<Object>>(url);
    }

}