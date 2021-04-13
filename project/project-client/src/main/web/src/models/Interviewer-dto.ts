export class InterviewerDto {

    private _id: string;
    private _firstName: string;
    private _lastName: string;

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

}