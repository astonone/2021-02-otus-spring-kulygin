export class CommentDto {

    private _id: string;
    private _text: string;
    private _commentatorName: string;
    private _isEdit: boolean = false;

    public static createNewObjectFromDto(element: CommentDto): CommentDto {
        return new CommentDto(element.id, element.commentatorName, element.text);
    }

    constructor(id: string, text: string, commentatorName: string) {
        this._id = id;
        this._text = text;
        this._commentatorName = commentatorName;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get commentatorName(): string {
        return this._commentatorName;
    }

    set commentatorName(value: string) {
        this._commentatorName = value;
    }

    get text(): string {
        return this._text;
    }

    set text(value: string) {
        this._text = value;
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
            text: this._text,
            commentatorName: this._commentatorName
        }
    }

}