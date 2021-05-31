import {Injectable} from '@angular/core';
import {LocalStorageService} from "./local-storage.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {BookDto} from "../models/book-dto";
import {CommentDto} from "../models/comment-dto";
import {BookListDto} from "../models/list/book-list-dto";
import {UserService} from "./user.service";

@Injectable({
    providedIn: 'root'
})

export class BookService {

    private readonly SERVER_URL: string;

    private readonly GET_ALL: string;
    private readonly DELETE: string;
    private readonly SAVE: string;
    private readonly GET_BY_ID: string;
    private readonly ADD_COMMENT: string;
    private readonly REMOVE_COMMENT: string;

    constructor(private localStorageService: LocalStorageService,
                private http: HttpClient,
                private userService: UserService) {
        this.SERVER_URL = this.localStorageService.getServerURL();
        this.GET_ALL = this.SERVER_URL + '/book/';
        this.DELETE = this.SERVER_URL + '/book/{id}';
        this.SAVE = this.SERVER_URL + '/book/';
        this.GET_BY_ID = this.SERVER_URL + '/book/{id}';
        this.ADD_COMMENT = this.SERVER_URL + '/book/{id}/comment';
        this.REMOVE_COMMENT = this.SERVER_URL + '/book/{id}/comment/{commentId}';
    }

    public getAll(): Observable<BookListDto> {
        return this.http.get<BookListDto>(this.GET_ALL, this.userService.getOptions());
    }

    public save(book: BookDto): Observable<BookDto> {
        return this.http.post<BookDto>(this.SAVE, BookDto.createNewObjectFromDto(book).toObject(),
            this.userService.getOptions());
    }

    public removeById(id: string): Observable<Object> {
        const regExp = /{id}/gi;
        const url = this.DELETE.replace(regExp, id);
        return this.http.delete<Observable<Object>>(url, this.userService.getOptions());
    }

    public getById(id: string): Observable<BookDto> {
        const regExp = /{id}/gi;
        const url = this.GET_BY_ID.replace(regExp, id);
        return this.http.get<BookDto>(url, this.userService.getOptions());
    }

    public addComment(comment: CommentDto, currentBookId: string): Observable<BookDto> {
        const regExp = /{id}/gi;
        const url = this.ADD_COMMENT.replace(regExp, currentBookId);
        return this.http.post<BookDto>(url, CommentDto.createNewObjectFromDto(comment).toObject(),
            this.userService.getOptions());
    }

    public removeComment(id: string, currentBookId: string): Observable<BookDto> {
        const regExp = /{id}/gi;
        const regExp2 = /{commentId}/gi;
        let url = this.REMOVE_COMMENT.replace(regExp, currentBookId);
        url = url.replace(regExp2, id);
        return this.http.delete<BookDto>(url, this.userService.getOptions());
    }

}