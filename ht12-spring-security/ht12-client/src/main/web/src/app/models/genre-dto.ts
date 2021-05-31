export class GenreDto {

    private _id: string;
    private _name: string;
    private _isEdit: boolean = false;

    public static createNewObjectFromDto(element: GenreDto): GenreDto {
        return new GenreDto(element.id, element.name);
    }

    constructor(id: string, name: string) {
        this._id = id;
        this._name = name;
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

    get isEdit(): boolean {
        return this._isEdit;
    }

    set isEdit(value: boolean) {
        this._isEdit = value;
    }

    public toObject() {
        return {
            id: this._id,
            name: this._name
        }
    }

}