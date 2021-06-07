import {BookDto} from "../book-dto";

export class BookListDto {

    private _books: BookDto[] = [];

    constructor(books: BookDto[]) {
        this._books = books;
    }

    get books(): BookDto[] {
        return this._books;
    }

    set books(value: BookDto[]) {
        this._books = value;
    }

}