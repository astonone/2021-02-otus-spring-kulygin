import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {BookDto} from "../models/book-dto";
import {GenreDto} from "../models/genre-dto";
import {AuthorDto} from "../models/author-dto";

@Injectable({
    providedIn: 'root'
})
export class LocalStorageService {

    private _books: BookDto[] = [];
    private _genres: GenreDto[] = [];
    private _authors: AuthorDto[] = [];

    private isProd: boolean = environment.production;

    private HOST_DEV = '/api';

    private HOST_PROD = 'http://localhost:8080/api';

    private readonly SERVER_URL: string;

    constructor() {
        this.SERVER_URL = this.isProd ? this.HOST_PROD : this.HOST_DEV;
    }

    public getStorage = () => localStorage.getItem('isRemember') === 'true' ? localStorage : sessionStorage;

    public getServerURL() {
        return this.SERVER_URL;
    }

    get books(): BookDto[] {
        return this._books;
    }

    set books(value: BookDto[]) {
        this._books = value;
    }

    get genres(): GenreDto[] {
        return this._genres;
    }

    set genres(value: GenreDto[]) {
        this._genres = value;
    }

    get authors(): AuthorDto[] {
        return this._authors;
    }

    set authors(value: AuthorDto[]) {
        this._authors = value;
    }

}