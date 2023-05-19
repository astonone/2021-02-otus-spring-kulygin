export class UserDto {

    private _id: string;
    private _username: string;
    private _password: string;
    private _passwordRepeat: string;

    public static createNewObjectFromDto(element: UserDto): UserDto {
        return new UserDto(element.id, element.username, element.password);
    }

    constructor(id: string, username: string, password: string) {
        this._id = id;
        this._username = username;
        this._password = password;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
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

    public toObject() {
        return {
            id: this._id,
            username: this._username,
            password: this._password
        }
    }

}