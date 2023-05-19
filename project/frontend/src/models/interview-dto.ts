import {CandidateDto} from "./candidate-dto";
import {InterviewerDto} from "./Interviewer-dto";
import {TemplateDto} from "./template-dto";

export class InterviewDto {

    private _id: string;
    private _candidate: CandidateDto;
    private _interviewer: InterviewerDto;
    private _interviewDateTime: string;
    private _interviewDate: string;
    private _interviewTime: string;
    private _interviewTemplate: TemplateDto;
    private _interviewStatus: string;
    private _totalMark: number
    private _totalComment: string;
    private _desiredSalary: string;
    private _decisionStatus: string;
    private _isEdit: boolean = false;
    private _isNew: boolean = false;

    public static createNewObjectFromDto(interviewDto: InterviewDto): InterviewDto {
        return new InterviewDto(interviewDto.id, interviewDto.candidate, interviewDto.interviewer, interviewDto.interviewDateTime,
            interviewDto.interviewTemplate, interviewDto.interviewStatus, interviewDto.totalMark, interviewDto.totalComment,
            interviewDto.desiredSalary, interviewDto.decisionStatus);
    }

    static getDate(interviewDateTime: string) {
        return interviewDateTime.split(" ")[0];
    }

    static getTime(interviewDateTime: string) {
        const convertTime24to12 = time24h => {
            // Check correct time format and split into components
            time24h = time24h.toString().match(/^([01]\d|2[0-3])(:)([0-5]\d)(:[0-5]\d)?$/) || [time24h];

            if (time24h.length > 1) { // If time format correct
                time24h = time24h.slice(1);  // Remove full string match value
                time24h[5] = +time24h[0] < 12 ? ' AM' : ' PM'; // Set AM/PM
                time24h[0] = +time24h[0] % 12 || 12; // Adjust hours
            }
            return time24h.join(''); // return adjusted time or original string
        }
        let time24h = interviewDateTime.split(" ")[1].split(":");
        return convertTime24to12(time24h[0] + ':' + time24h[1]);
    }

    constructor(id: string, candidate: CandidateDto, interviewer: InterviewerDto, interviewDateTime: string, interviewTemplate: TemplateDto, interviewStatus: string, totalMark: number, totalComment: string, desiredSalary: string, decisionStatus: string) {
        this._id = id;
        this._candidate = candidate;
        this._interviewer = interviewer;
        this._interviewDateTime = interviewDateTime;
        this._interviewTemplate = interviewTemplate;
        this._interviewStatus = interviewStatus;
        this._totalMark = totalMark;
        this._totalComment = totalComment;
        this._desiredSalary = desiredSalary;
        this._decisionStatus = decisionStatus;
    }

    get id(): string {
        return this._id;
    }

    set id(value: string) {
        this._id = value;
    }

    get candidate(): CandidateDto {
        return this._candidate;
    }

    set candidate(value: CandidateDto) {
        this._candidate = value;
    }

    get interviewer(): InterviewerDto {
        return this._interviewer;
    }

    set interviewer(value: InterviewerDto) {
        this._interviewer = value;
    }

    get interviewDateTime(): string {
        return this._interviewDateTime;
    }

    set interviewDateTime(value: string) {
        this._interviewDateTime = value;
    }

    get interviewTemplate(): TemplateDto {
        return this._interviewTemplate;
    }

    set interviewTemplate(value: TemplateDto) {
        this._interviewTemplate = value;
    }

    get interviewStatus(): string {
        return this._interviewStatus;
    }

    set interviewStatus(value: string) {
        this._interviewStatus = value;
    }

    get totalMark(): number {
        return this._totalMark;
    }

    set totalMark(value: number) {
        this._totalMark = value;
    }

    get totalComment(): string {
        return this._totalComment;
    }

    set totalComment(value: string) {
        this._totalComment = value;
    }

    get desiredSalary(): string {
        return this._desiredSalary;
    }

    set desiredSalary(value: string) {
        this._desiredSalary = value;
    }

    get decisionStatus(): string {
        return this._decisionStatus;
    }

    set decisionStatus(value: string) {
        this._decisionStatus = value;
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

    get interviewDate(): string {
        return this._interviewDate;
    }

    set interviewDate(value: string) {
        this._interviewDate = value;
    }

    get interviewTime(): string {
        return this._interviewTime;
    }

    set interviewTime(value: string) {
        this._interviewTime = value;
    }

    public toObject(): any {
        return {
            id: this._id,
            candidate: {
                id: this._candidate.id
            },
            interviewer: {
                id: this._interviewer.id
            },
            interviewDateTime: this._interviewDateTime,
            interviewTemplate: {
                id: this.interviewTemplate.id
            },
            interviewStatus: this._interviewStatus,
            totalMark: this._totalMark,
            totalComment: this._totalComment,
            desiredSalary: this._desiredSalary,
            decisionStatus: this._decisionStatus,
        }
    }

    public buildDateAndTime() {
        const convertTime12to24 = time12h => {
            const [time, modifier] = time12h.split(" ");

            let [hours, minutes] = time.split(":");

            if (hours === "12") {
                hours = "00";
            }

            if (modifier === "PM") {
                hours = parseInt(hours, 10) + 12;
            }

            if (hours.length < 2) {
                hours = '0' + hours;
            }

            return `${hours}:${minutes}:00`;
        };
        const isString = value => {
            return typeof value === 'string' || value instanceof String;
        }
        const getDateStringFromDate = date => {
            if (!isString(date)) {
                date.setDate(date.getDate() + 1);
            }
            const dateString = isString(date) ? date : date.toISOString().split('T')[0];
            return dateString;
        }
        this._interviewDateTime = getDateStringFromDate(this._interviewDate) + ' ' + convertTime12to24(this._interviewTime);
    }



}