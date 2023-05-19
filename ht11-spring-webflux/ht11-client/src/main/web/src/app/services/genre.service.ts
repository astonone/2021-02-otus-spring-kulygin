import {Injectable} from '@angular/core';
import {SharedService} from "./shared.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GenreDto} from "../models/genre-dto";
import {GenreListDto} from "../models/list/genre-list-dto";

@Injectable({
    providedIn: 'root'
})

export class GenreService {

    private readonly SERVER_URL: string;

    private readonly GET_ALL: string;
    private readonly DELETE: string;
    private readonly SAVE: string;

    constructor(private shared: SharedService,
                private http: HttpClient) {
        this.SERVER_URL = this.shared.getServerURL();
        this.GET_ALL = this.SERVER_URL + '/genre/';
        this.DELETE = this.SERVER_URL + '/genre/{id}';
        this.SAVE = this.SERVER_URL + '/genre/';
    }

    public getAll(): Observable<GenreListDto> {
        return this.http.get<GenreListDto>(this.GET_ALL);
    }

    public save(genre: GenreDto): Observable<GenreDto> {
        return this.http.post<GenreDto>(this.SAVE, GenreDto.createNewObjectFromDto(genre).toObject());
    }

    public removeById(id: string): Observable<Object> {
        const regExp = /{id}/gi;
        const url = this.DELETE.replace(regExp, id);
        return this.http.delete<Observable<Object>>(url);
    }

}