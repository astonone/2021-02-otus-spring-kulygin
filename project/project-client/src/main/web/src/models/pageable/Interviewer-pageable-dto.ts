import {InterviewerDto} from "../Interviewer-dto";

export class InterviewerPageableDto {

    private _totalSize: number;
    private _totalPageSize: number;
    private _currentPageSize: number;
    private _page: number;
    private _pageSize: number;
    private _interviewers: InterviewerDto[] = [];

    constructor(totalSize: number, totalPageSize: number, currentPageSize: number, page: number, pageSize: number, interviewers: InterviewerDto[]) {
        this._totalSize = totalSize;
        this._totalPageSize = totalPageSize;
        this._currentPageSize = currentPageSize;
        this._page = page;
        this._pageSize = pageSize;
        this._interviewers = interviewers;
    }

    get totalSize(): number {
        return this._totalSize;
    }

    set totalSize(value: number) {
        this._totalSize = value;
    }

    get totalPageSize(): number {
        return this._totalPageSize;
    }

    set totalPageSize(value: number) {
        this._totalPageSize = value;
    }

    get currentPageSize(): number {
        return this._currentPageSize;
    }

    set currentPageSize(value: number) {
        this._currentPageSize = value;
    }

    get page(): number {
        return this._page;
    }

    set page(value: number) {
        this._page = value;
    }

    get pageSize(): number {
        return this._pageSize;
    }

    set pageSize(value: number) {
        this._pageSize = value;
    }

    get interviewers(): InterviewerDto[] {
        return this._interviewers;
    }

    set interviewers(value: InterviewerDto[]) {
        this._interviewers = value;
    }

}