import {GenreDto} from "../genre-dto";

export class GenreListDto {

    private _genres: GenreDto[] = [];

    constructor(genres: GenreDto[]) {
        this._genres = genres;
    }

    get genres(): GenreDto[] {
        return this._genres;
    }

    set genres(value: GenreDto[]) {
        this._genres = value;
    }

}