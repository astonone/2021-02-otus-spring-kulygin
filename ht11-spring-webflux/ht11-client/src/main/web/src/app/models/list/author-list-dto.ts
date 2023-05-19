import {AuthorDto} from "../author-dto";

export class AuthorListDto {

    private _authors: AuthorDto[] = [];

    constructor(authors: AuthorDto[]) {
        this._authors = authors;
    }

    get authors(): AuthorDto[] {
        return this._authors;
    }

    set authors(value: AuthorDto[]) {
        this._authors = value;
    }

}