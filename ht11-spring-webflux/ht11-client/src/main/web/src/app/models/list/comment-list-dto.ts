import {CommentDto} from "../comment-dto";

export class CommentListDto {

    private _comments: CommentDto[] = [];

    constructor(comments: CommentDto[]) {
        this._comments = comments;
    }

    get comments(): CommentDto[] {
        return this._comments;
    }

    set comments(value: CommentDto[]) {
        this._comments = value;
    }
}