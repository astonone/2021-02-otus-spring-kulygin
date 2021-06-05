export class InterviewerDto {

    private _id: string;
    private _firstName: string;
    private _lastName: string;
    private _positionType: string;
    private _username: string;
    private _password: string;
    private _passwordRepeat: string;
    private _role: string;
    private _secretKey: string;
    private _create: boolean = false;
    private _isEdit: boolean = false;
    private _isNew: boolean = false;

    public static createNewObjectFromDto(interviewer: InterviewerDto): InterviewerDto {
        return new InterviewerDto(interviewer.id, interviewer.firstName, interviewer.lastName, interviewer.positionType,
            interviewer.username, interviewer.password, interviewer.role, interviewer.secretKey);
    }

    constructor(id: string, firstName: string, lastName: string, positionType: string, username: string, password: string, role: string, secretKey: string) {
        this._id = id;
        this._firstName = firstName;
        this._lastName = lastName;
        this._positionType = positionType;
        this._username = username;
        this._password = password;
        this._role = role;
        this._secretKey = secretKey;
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

    get positionType(): string {
        return this._positionType;
    }

    set positionType(value: string) {
        this._positionType = value;
    }

    get username(): string {
        return this._username;
    }

    set username(value: string) {
        this._username = value;
    }

    get password(): string {
        return this._password;
    }

    set password(value: string) {
        this._password = value;
    }

    get passwordRepeat(): string {
        return this._passwordRepeat;
    }

    set passwordRepeat(value: string) {
        this._passwordRepeat = value;
    }

    get secretKey(): string {
        return this._secretKey;
    }

    set secretKey(value: string) {
        this._secretKey = value;
    }

    get isEdit(): boolean {
        return this._isEdit;
    }

    set isEdit(value: boolean) {
        this._isEdit = value;
    }

    get isNew(): boolean {
        return this._isNew;
    }

    set isNew(value: boolean) {
        this._isNew = value;
    }

    get create(): boolean {
        return this._create;
    }

    set create(value: boolean) {
        this._create = value;
    }

    get role(): string {
        return this._role;
    }

    set role(value: string) {
        this._role = value;
    }

    public toObject(): any {
        return {
            id: this._id,
            firstName: this._firstName,
            lastName: this._lastName,
            positionType: this._positionType,
            username: this._username,
            password: this._password,
            secretKey: this._secretKey,
            role: this._role,
            create: this._create
        }
    }

}