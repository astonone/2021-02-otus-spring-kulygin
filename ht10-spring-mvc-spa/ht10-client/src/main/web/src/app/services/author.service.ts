import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AuthorDto} from "../models/author-dto";
import {AuthorListDto} from "../models/list/author-list-dto";

@Injectable({
    providedIn: 'root'
})

export class AuthorService {

    private readonly SERVER_URL: string;

    private readonly GET_ALL: string;
    private readonly DELETE: string;
    private readonly SAVE: string;

    constructor(private shared: SharedService,
                private http: HttpClient) {
        this.SERVER_URL = this.shared.getServerURL();
        this.GET_ALL = this.SERVER_URL + '/author/';
        this.DELETE = this.SERVER_URL + '/author/{id}';
        this.SAVE = this.SERVER_URL + '/author/';
    }

    public getAll(): Observable<AuthorListDto> {
        return this.http.get<AuthorListDto>(this.GET_ALL);
    }

    public save(author: AuthorDto): Observable<AuthorDto> {
        return this.http.post<AuthorDto>(this.SAVE, AuthorDto.createNewObjectFromDto(author).toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExp = /{id}/gi;
        const url = this.DELETE.replace(regExp, id);
        return this.http.delete<Observable<Object>>(url);
    }

}