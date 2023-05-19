export class InterviewTemplateCriteriaDto {

    private _id: string;
    private _name: string;
    private _positionType: string;
    private _mark: number;
    private _interviewerComment: string;
    private _isEdit: boolean = false;

    public static createNewObjectFromDto(criteria: InterviewTemplateCriteriaDto): InterviewTemplateCriteriaDto {
        return new InterviewTemplateCriteriaDto(criteria.id, criteria.name, criteria.positionType, criteria.mark, criteria.interviewerComment);
    }

    constructor(id: string, name: string, positionType: string, mark: number, interviewerComment: string) {
        this._id = id;
        this._name = name;
        this._positionType = positionType;
        this._mark = mark;
        this._interviewerComment = interviewerComment;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get positionType(): string {
        return this._positionType;
    }

    set positionType(value: string) {
        this._positionType = value;
    }

    get mark(): number {
        return this._mark;
    }

    set mark(value: number) {
        this._mark = value;
    }

    get interviewerComment(): string {
        return this._interviewerComment;
    }

    set interviewerComment(value: string) {
        this._interviewerComment = value;
    }

    get isEdit(): boolean {
        return this._isEdit;
    }

    set isEdit(value: boolean) {
        this._isEdit = value;
    }

    public toObject(): any {
        return {
            id: this._id,
            name: this._name,
            positionType: this._positionType,
            mark: this._mark,
            interviewerComment: this._interviewerComment,
        }
    }

}