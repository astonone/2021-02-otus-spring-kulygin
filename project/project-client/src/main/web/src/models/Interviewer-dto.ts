export class InterviewerDto {

    private _id: string;
    private _firstName: string;
    private _lastName: string;
    private _isEdit: boolean = false;

    public static createNewObjectFromDto(interviewer: InterviewerDto): InterviewerDto {
        return new InterviewerDto(interviewer.id, interviewer.firstName, interviewer.lastName);
    }

    constructor(id: string, firstName: string, lastName: string) {
        this._id = id;
        this._firstName = firstName;
        this._lastName = lastName;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get firstName(): string {
        return this._firstName;
    }

    set firstName(value: string) {
        this._firstName = value;
    }

    get lastName(): string {
        return this._lastName;
    }

    set lastName(value: string) {
        this._lastName = value;
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
            firstName: this._firstName,
            lastName: this._lastName
        }
    }
}