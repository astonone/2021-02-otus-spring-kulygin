import {AuthorDto} from "./author-dto";
import {GenreDto} from "./genre-dto";
import {CommentDto} from "./comment-dto";

export class BookDto {

    private _id: string;
    private _title: string;
    private _author: AuthorDto;
    private _genre: GenreDto;
    private _comments: CommentDto[] = [];
    private _isEdit: boolean = false;

    public static createNewObjectFromDto(element: BookDto): BookDto {
        return new BookDto(element.id, element.title, element.author, element.genre, element.comments);
    }

    constructor(id: string, title: string, author: AuthorDto, genre: GenreDto, comments: CommentDto[]) {
        this._id = id;
        this._title = title;
        this._author = author;
        this._genre = genre;
        this._comments = comments;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        this._title = value;
    }

    get author(): AuthorDto {
        return this._author;
    }

    set author(value: AuthorDto) {
        this._author = value;
    }

    get genre(): GenreDto {
        return this._genre;
    }

    set genre(value: GenreDto) {
        this._genre = value;
    }

    get comments(): CommentDto[] {
        return this._comments;
    }

    set comments(value: CommentDto[]) {
        this._comments = value;
    }

    get isEdit(): boolean {
        return this._isEdit;
    }

    set isEdit(value: boolean) {
        this._isEdit = value;
    }

    public toObject() {
        return {
            id: this._id,
            title: this._title,
            genre: {
                id: this._genre ? this._genre.id : null,
                name: this._genre ? this._genre.name : null
            },
            author: {
                id: this._author ? this._author.id : null,
                firstName: this._author ? this._author.firstName : null,
                lastName: this._author ? this._author.lastName : null
            }
        }
    }

}